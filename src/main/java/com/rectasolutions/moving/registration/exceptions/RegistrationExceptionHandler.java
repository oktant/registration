package com.rectasolutions.moving.registration.exceptions;

import com.rectasolutions.moving.registration.entities.Response;
import com.rectasolutions.moving.registration.messages.Message;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.ws.rs.NotFoundException;

/**
 * Created by Dell on 16-Jul-18.
 */
@RestControllerAdvice
public class RegistrationExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> notFound(){
        Response response=new Response(Message.USER_NOT_EXISTS.getMessageText(), Message.USER_NOT_EXISTS.getMessageCode());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Response> empty(){
        Response response=new Response(Message.USER_NOT_EXISTS.getMessageText(), Message.USER_NOT_EXISTS.getMessageCode());

        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, NotAllMandatoryFieldsAreSpecified.class})
    public ResponseEntity<Response> badRequest(){
        Response response=new Response(Message.NOT_ALL_MANDATORY_FIELDS.getMessageText(), Message.NOT_ALL_MANDATORY_FIELDS.getMessageCode());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Response> userExists(){
        Response response=new Response(Message.USER_ALREADY_EXISTS.getMessageText(), Message.USER_ALREADY_EXISTS.getMessageCode());
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Response> userDoesntExist(){
        Response response=new Response(Message.USER_NOT_EXISTS.getMessageText(), Message.USER_NOT_EXISTS.getMessageCode());
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(CountryNotFound.class)
    public ResponseEntity<Response> countryDoesntExist(){
        Response response=new Response(Message.COUNTRY_NOT_EXISTS.getMessageText(), Message.COUNTRY_NOT_EXISTS.getMessageCode());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

    }


}

