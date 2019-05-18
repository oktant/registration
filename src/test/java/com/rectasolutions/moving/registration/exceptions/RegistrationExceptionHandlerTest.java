package com.rectasolutions.moving.registration.exceptions;

import com.rectasolutions.moving.registration.entities.Response;
import com.rectasolutions.moving.registration.messages.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)

public class RegistrationExceptionHandlerTest {

    @InjectMocks
    RegistrationExceptionHandler registrationExceptionHandler;

    @Test
    public void empty() {

        Response response=new Response(Message.USER_NOT_EXISTS.getMessageText(), Message.USER_NOT_EXISTS.getMessageCode());
        assertEquals(response.getCode(), registrationExceptionHandler.empty().getBody().getCode());
    }

    @Test
    public void badRequest() {

        Response response=new Response(Message.NOT_ALL_MANDATORY_FIELDS.getMessageText(), Message.NOT_ALL_MANDATORY_FIELDS.getMessageCode());
        assertEquals(response.getCode(), registrationExceptionHandler.badRequest().getBody().getCode());
    }

    @Test
    public void userExists() {
        Response response=new Response(Message.USER_ALREADY_EXISTS.getMessageText(), Message.USER_ALREADY_EXISTS.getMessageCode());
        assertEquals(response.getCode(), registrationExceptionHandler.userExists().getBody().getCode());
    }

    @Test
    public void userDoesntExist() {
        Response response=new Response(Message.USER_NOT_EXISTS.getMessageText(), Message.USER_NOT_EXISTS.getMessageCode());
        assertEquals(response.getCode(), registrationExceptionHandler.userDoesntExist().getBody().getCode());

    }

    @Test
    public void countryDoesntExist() {

        Response response=new Response(Message.COUNTRY_NOT_EXISTS.getMessageText(), Message.COUNTRY_NOT_EXISTS.getMessageCode());
        assertEquals(response.getCode(), registrationExceptionHandler.countryDoesntExist().getBody().getCode());

    }
}

