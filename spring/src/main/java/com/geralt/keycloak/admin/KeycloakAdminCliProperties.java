package com.geralt.keycloak.admin;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin.client")
@Getter
@Setter
public class KeycloakAdminCliProperties {

  private String authServerUrl;
  private String realm;
  private String resource;
  private String secret;

}
