package com.rectasolutions.moving.registration.services;

import com.rectasolutions.moving.registration.entities.Country;
import com.rectasolutions.moving.registration.entities.User;
import com.rectasolutions.moving.registration.exceptions.UserExistsException;
import com.rectasolutions.moving.registration.messages.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.omg.CORBA.Any;
import org.springframework.test.util.ReflectionTestUtils;

import javax.ws.rs.core.Response;
import java.lang.reflect.Method;

import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.*;
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


    @Before
    public void createUser(){
        country.setCountryCode("SE");
        country.setCountryName("Sweden");
        country.setPhoneCode("07777777");
        country.setId(1);


        user.setEmail("test@recta.com");
        user.setPassword("dadasda");
        user.setCity("dasfdasd");
        user.setCountry(1);

        ReflectionTestUtils.setField(registrationServiceMock, "clientSecret", "asdasd");
        ReflectionTestUtils.setField(registrationServiceMock, "client", "http://foo");
        ReflectionTestUtils.setField(registrationServiceMock, "movingKeycloakAuthUrl", "dasda");
        ReflectionTestUtils.setField(registrationServiceMock, "movingRole", "dasdass");

    }

    @Test(expected = UserExistsException.class)
    public void addUserIntoKeycloak()  {
        when(countryServiceMock.getCountry(1)).thenReturn(country);
        UsersResource usersResource=mock(UsersResource.class);
        when(realmResource.users()).thenReturn(usersResource);
        javax.ws.rs.core.Response response=javax.ws.rs.core.Response.status(Response.Status.CONFLICT).build();
        when(usersResource.create(any())).thenReturn(response);
        registrationServiceMock.addUserIntoKeycloak(user);

    }

    @Test
    public void removeUserFromKeycloak() {
    }

    @Test
    public void getTokens() {
    }

    @Test
    public void getUserFromKeyclock() {
    }
}
