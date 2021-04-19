package com.geralt.keycloak.admin;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

@RequiredArgsConstructor
@Slf4j
public class KeycloakUserProvider {

  private final Keycloak keycloak;
  private final KeycloakSpringBootProperties keycloakProperties;
  private final KeycloakAdminCliProperties adminCliProperties;


  public List<UserRepresentation> getRealmUser() {
    return keycloak.realm(adminCliProperties.getRealm()).users().list();
  }

  public List<RoleRepresentation> getClientRoles() {
    return keycloak.realm(keycloakProperties.getRealm()).clients().get(keycloakProperties.getResource()).roles().list();
  }

}
