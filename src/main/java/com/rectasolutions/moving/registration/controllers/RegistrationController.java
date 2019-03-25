package com.rectasolutions.moving.registration.controllers;

import com.rectasolutions.moving.registration.config.security.ClientToken;
import com.rectasolutions.moving.registration.entities.Country;
import com.rectasolutions.moving.registration.entities.LoginUser;
import com.rectasolutions.moving.registration.entities.Response;
import com.rectasolutions.moving.registration.entities.User;

import com.rectasolutions.moving.registration.services.CountryService;
import com.rectasolutions.moving.registration.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Created by Dell on 27-Jun-18.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class RegistrationController {
    private RegistrationService registrationService;
    private CountryService countryService;

    @Autowired
    public RegistrationController(CountryService countryService, RegistrationService registrationService){
        this.countryService=countryService;
        this.registrationService=registrationService;

    }


    @PostMapping("/users")
    public ResponseEntity<Response> registerUser(@Valid @RequestBody  User user){
        return new ResponseEntity<>(registrationService.addUserIntoKeycloak(user), HttpStatus.CREATED);

    }

    @DeleteMapping("/users")
    public ResponseEntity<String> removeUser(Principal principal){
        return registrationService.removeUserFromKeycloak(principal.getName());
    }

    @PostMapping("/token")
    public ResponseEntity<ClientToken> getUser(@Valid @RequestBody LoginUser loginUser) throws IOException {
        return new ResponseEntity<>(registrationService.getTokens(loginUser), HttpStatus.OK);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getCountries(){
        return new ResponseEntity(countryService.getAlCountries(),HttpStatus.OK);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) throws ServletException{
        request.logout();
    }


}
