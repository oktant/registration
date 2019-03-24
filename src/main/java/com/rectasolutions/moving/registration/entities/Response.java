package com.rectasolutions.moving.registration.entities;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class Response {

    public Response(String message, int code) {
        this.message = message;
        this.code = code;
    }

    @NotNull
    @Email
    @NotBlank
    private String message;

    @NotNull
    @Email
    @NotBlank
    private int code;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
