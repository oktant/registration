package com.rectasolutions.moving.registration.config.security;

import org.springframework.stereotype.Component;

@Component
public class ClientToken {


    private String accessToken;
    private Integer expiresIn;
    private String refreshToken;


    public String getAccessToken() {
        return accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
