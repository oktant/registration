package com.rectasolutions.moving.registration.entities;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class LoginUser {


    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;





    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
