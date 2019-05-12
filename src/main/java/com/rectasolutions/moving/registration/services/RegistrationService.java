package com.rectasolutions.moving.registration.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rectasolutions.moving.registration.config.security.ClientToken;
import com.rectasolutions.moving.registration.config.security.TokenCollection;
import com.rectasolutions.moving.registration.entities.LoginUser;
import com.rectasolutions.moving.registration.entities.User;
import com.rectasolutions.moving.registration.entities.UserDB;
import com.rectasolutions.moving.registration.exceptions.UserExistsException;
import com.rectasolutions.moving.registration.exceptions.UserNotFound;
import com.rectasolutions.moving.registration.messages.Message;
import com.rectasolutions.moving.registration.repositories.UserDBRepository;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.asList;


/**
 * Created by Dell on 10-Jul-18.
 */
@Service
public class RegistrationService {
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.resource}")
    private String client;


    @Value("${moving.client.keycloak.url}")
    private String movingKeycloakAuthUrl;
    @Value("${moving.standard.keycloak.role}")
    private String movingRole;


    private ClientToken clientToken;

    private TokenCollection tokenCollection;


    private UserDBRepository userDBRepository;
    private CountryService countryService;

    private RealmResource realmResource;

    @Autowired
    public RegistrationService(UserDBRepository userDBRepository, CountryService countryService,
                               TokenCollection tokenCollection, ClientToken clientToken, RealmResource realmResource) {
        this.userDBRepository = userDBRepository;
        this.countryService = countryService;
        this.tokenCollection=tokenCollection;
        this.clientToken=clientToken;
        this.realmResource=realmResource;
    }





    //--- ADD USER ----//
    public com.rectasolutions.moving.registration.entities.Response addUserIntoKeycloak(User user) {


        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmail(user.getEmail());
        Map<String, List<String>> stringMap = new HashMap<>();
        List<String> countryList = new ArrayList<>();
        countryList.add((countryService.getCountry(user.getCountry())).getCountryName());
        stringMap.put("country", countryList);
        List<String> cityList = new ArrayList<>();
        cityList.add(user.getCity());
        stringMap.put("city", cityList);
        List<String> phoneList = new ArrayList<>();
        phoneList.add((countryService.getCountry(user.getCountry())).getPhoneCode() + user.getPhoneNumber());
        stringMap.put("phoneNumber", phoneList);
        userRepresentation.setAttributes(stringMap);
        return getResponseFromCreationOfUserKeycloak(userRepresentation, user.getPassword());

    }

    private  com.rectasolutions.moving.registration.entities.Response getResponseFromCreationOfUserKeycloak(

        UserRepresentation userRepresentation, String password){
        UsersResource usersResource = realmResource.users();
        Response response=usersResource.create(userRepresentation);

        if (response.getStatus() == 409) {
            throw new UserExistsException();
        } else {
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            //Add role to new User
            ClientRepresentation clientRep = realmResource.clients().findByClientId(client).get(0);
            RoleRepresentation clientRoleRep = realmResource.clients().get(clientRep.getId()).roles().get(movingRole).toRepresentation();
            realmResource.users().get(userId).roles().clientLevel(clientRep.getId()).add(Arrays.asList(clientRoleRep));


            // Define password credential
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(password);

            // Set password credential
            realmResource.users().get(userId).resetPassword(passwordCred);

            // Add user into DB
            addUserIntoDB(userId);

            return new com.rectasolutions.moving.registration.entities.Response(Message.SUCCESSFUL_USER_CREATION.getMessageText(), Message.SUCCESSFUL_USER_CREATION.getMessageCode());
        }
    }

    //--- REMOVE USER ----//
    public ResponseEntity<String> removeUserFromKeycloak(String userId) {
        removeUserFromDB(userId);

        realmResource.users().get(userId).remove();
        return new ResponseEntity<>("User was deleted", HttpStatus.OK);
    }

    public ClientToken getTokens(LoginUser loginUser) throws IOException {


        HttpPost post = new HttpPost(movingKeycloakAuthUrl);
        List<NameValuePair> params = asList(new BasicNameValuePair("grant_type", "password"),
                new BasicNameValuePair("client_id", client),
                new BasicNameValuePair("username", loginUser.getUsername()),
                new BasicNameValuePair("password", loginUser.getPassword()),
                new BasicNameValuePair("client_secret", clientSecret)

        );

        post.setEntity(new UrlEncodedFormEntity(params));
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");
        ClientToken clientTokenCollection = getTokenCollection(post);
        if (clientTokenCollection == null || clientTokenCollection.getAccessToken() == null) {
            throw new UserNotFound();
        }
        return getTokenCollection(post);
    }


    private ClientToken getTokenCollection(HttpPost post) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            return httpclient.execute(post, response -> {
                ObjectMapper mapper = new ObjectMapper();
                int status = response.getStatusLine().getStatusCode();


                if (status >= 200 && status < 300) {
                    tokenCollection = mapper.readValue(response.getEntity().getContent(), TokenCollection.class);
                    clientToken.setAccessToken(tokenCollection.getAccessToken());
                    clientToken.setRefreshToken(tokenCollection.getRefreshToken());
                    clientToken.setExpiresIn(tokenCollection.getExpiresIn());
                    return clientToken;

                } else {
                    throw new UserNotFound();
                }
            });
        }
    }

    private void addUserIntoDB(String userId) {
        UserDB userDB = new UserDB();
        userDB.setId(userId);
        userDB.setStatus("offline");
        userDB.setLatitude(0.00);
        userDB.setLongitude(0.00);
        userDBRepository.save(userDB);
    }

    private void removeUserFromDB(String userId) {
        userDBRepository.deleteById(userId);
    }


}
