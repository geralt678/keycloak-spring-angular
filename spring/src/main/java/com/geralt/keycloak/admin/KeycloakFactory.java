package com.geralt.keycloak.admin;

import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

@RequiredArgsConstructor
public class KeycloakFactory {

  private final KeycloakAdminCliProperties properties;

  public Keycloak createKeycloakAuthorizedBySecret() {
    return KeycloakBuilder.builder()
      .serverUrl(properties.getAuthServerUrl())
      .realm(properties.getRealm())
      .clientId(properties.getResource())
      .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
      .clientSecret(properties.getSecret())
      .resteasyClient(createResteasyClient())
      .build();
  }

  private ResteasyClient createResteasyClient() {
    return new ResteasyClientBuilder()
      .connectionPoolSize(10)
      .build();
  }

}
