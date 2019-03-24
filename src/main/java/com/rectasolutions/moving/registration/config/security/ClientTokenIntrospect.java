package com.rectasolutions.moving.registration.config.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class ClientTokenIntrospect {
    @JsonProperty("active")
    boolean active=false;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

