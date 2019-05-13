package com.rectasolutions.moving.registration.services;

import com.rectasolutions.moving.registration.entities.Country;
import com.rectasolutions.moving.registration.entities.User;
import com.rectasolutions.moving.registration.entities.UserDB;
import com.rectasolutions.moving.registration.exceptions.UserExistsException;
import com.rectasolutions.moving.registration.messages.Message;
import com.rectasolutions.moving.registration.repositories.UserDBRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class RegistrationServiceTest {
    private User user=new User();
    @Mock
    RealmResource realmResource;


    @Mock
    CountryService countryServiceMock;
    @InjectMocks
    RegistrationService registrationServiceMock;

    Country country=new Country();
    @Mock
    UserDBRepository userDBRepository;
    UserDB userDB;

    @Before
    public void createUser(){
        country.setCountryCode("SE");
        country.setCountryName("Sweden");
        country.setPhoneCode("07777777");
        country.setId(1);

        userDB=new UserDB();

        user.setEmail("test@recta.com");
        user.setPassword("dadasda");
        user.setCity("dasfdasd");
        user.setCountry(1);

        ReflectionTestUtils.setField(registrationServiceMock, "clientSecret", "asdasd");
        ReflectionTestUtils.setField(registrationServiceMock, "client", "test-client");
        ReflectionTestUtils.setField(registrationServiceMock, "movingKeycloakAuthUrl", "dasda");
        ReflectionTestUtils.setField(registrationServiceMock, "movingRole", "dasdass");

    }

    @Test(expected = UserExistsException.class)
    public void addUserIntoKeycloakWithConflict()  {
        when(countryServiceMock.getCountry(1)).thenReturn(country);
        UsersResource usersResource=mock(UsersResource.class);
        when(realmResource.users()).thenReturn(usersResource);
        javax.ws.rs.core.Response response=javax.ws.rs.core.Response.status(Response.Status.CONFLICT).build();
        when(usersResource.create(any())).thenReturn(response);
        registrationServiceMock.addUserIntoKeycloak(user);

    }
    @Test
    public void addUserIntoKeycloakSuccess() throws URISyntaxException {
        List<ClientRepresentation> clientRepresentationList=new ArrayList<>();
        ClientRepresentation clientRepresentation=new ClientRepresentation();
        clientRepresentationList.add(clientRepresentation);
        ClientResource clientResource=mock(ClientResource.class);
        ClientsResource clientsResource=mock(ClientsResource.class);
        RoleMappingResource roleMappingResource=mock(RoleMappingResource.class);
        RolesResource rolesResource=mock(RolesResource.class);
        RoleResource roleResource=mock(RoleResource.class);
        RoleScopeResource roleScopeResource=mock(RoleScopeResource.class);
        RoleRepresentation roleRepresentation=mock(RoleRepresentation.class);
        UsersResource usersResource=mock(UsersResource.class);
        UserResource userResource=mock(UserResource.class);

        when(countryServiceMock.getCountry(1)).thenReturn(country);
        when(realmResource.users()).thenReturn(usersResource);
        URI uri=new URI("dasdas");
        javax.ws.rs.core.Response response=javax.ws.rs.core.Response.status(Response.Status.OK).
                location(uri).build();
        when(usersResource.create(any())).thenReturn(response);
        when(realmResource.clients()).thenReturn(clientsResource);
        when(clientsResource.findByClientId("test-client")).thenReturn(clientRepresentationList);
        when(clientsResource.get(clientRepresentation.getId())).thenReturn(clientResource);
        when(clientResource.roles()).thenReturn(rolesResource);
        when(rolesResource.get("dasdass")).thenReturn(roleResource);
        when(roleResource.toRepresentation()).thenReturn(roleRepresentation);
        when(usersResource.get(anyString())).thenReturn(userResource);
        when(userResource.roles()).thenReturn(roleMappingResource);

        when(roleMappingResource.clientLevel(null)).thenReturn(roleScopeResource);
        UserDB userDB=mock(UserDB.class);
        when(userDBRepository.save(userDB)).thenReturn(userDB);
        assertEquals(Message.SUCCESSFUL_USER_CREATION.getMessageCode(),registrationServiceMock.addUserIntoKeycloak(user).getCode());

    }

    @Test
    public void removeUserFromKeycloak() {
        UsersResource usersResource=mock(UsersResource.class);
        UserResource userResource=mock(UserResource.class);

        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.get("aaa")).thenReturn(userResource);
        assertEquals("User was deleted", registrationServiceMock.removeUserFromKeycloak("aaa"));


    }

    @Test
    public void getUserFromKeyclock() {
    }
}
