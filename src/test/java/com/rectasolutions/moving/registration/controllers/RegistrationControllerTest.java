package com.rectasolutions.moving.registration.controllers;

import com.rectasolutions.moving.registration.config.security.ClientToken;
import com.rectasolutions.moving.registration.entities.LoginUser;
import com.rectasolutions.moving.registration.entities.Response;
import com.rectasolutions.moving.registration.entities.User;
import com.rectasolutions.moving.registration.services.CountryService;
import com.rectasolutions.moving.registration.services.RegistrationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)

public class RegistrationControllerTest {
    @InjectMocks
    RegistrationController registrationController;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private CountryService countryService;
    User user;


    @Before
    public void setUp(){
        user=new User();
    }

    @Test
    public void registerUser() {
        Response response=mock(Response.class);
        when(registrationService.addUserIntoKeycloak(user)).thenReturn(response);
        assertEquals(HttpStatus.CREATED,registrationController.registerUser(user).getStatusCode());

    }

    @Test
    public void removeUser() {

    }



    @Test
    public void getUser() throws IOException {
        LoginUser loginUser=new LoginUser();
        ClientToken clientToken=mock(ClientToken.class);
        when(registrationService.getTokens(loginUser)).thenReturn(clientToken);
        assertEquals(HttpStatus.OK,registrationController.getUser(loginUser).getStatusCode());
    }




    @Test
    public void getCountries() {
    }

    @Test
    public void logout() {
    }
}
