package com.rectasolutions.moving.registration.config.security;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Value("${recta.moving.keycloak.admin.user}")
    private String adminUser;

    @Value("${recta.moving.keycloak.admin.password}")
    private String adminPass;

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realmName;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.resource}")
    private String client;


    @Bean
    public RealmResource getRealm() {
        Keycloak keycloak = KeycloakBuilder.builder() //
                .serverUrl(serverUrl) //
                .realm(realmName) //
                .clientId(client) //
                .clientSecret(clientSecret)
                .username(adminUser) //
                .password(adminPass) //
                .build();
        return keycloak.realm(realmName);
    }
}
