package com.rectasolutions.moving.registration.entities;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Response {

    public Response(String message, int code) {
        this.message = message;
        this.code = code;
    }

    @NotNull
    @Email
    @NotEmpty
    private String message;

    @NotNull
    @Email
    @NotEmpty
    private int code;


    public String getMessage() {
        return message;
    }
    public int getCode() {
        return code;
    }

}
