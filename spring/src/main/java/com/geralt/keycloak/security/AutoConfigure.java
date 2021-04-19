package com.geralt.keycloak.security;

import com.geralt.keycloak.admin.KeycloakAdminCliProperties;
import com.geralt.keycloak.admin.KeycloakFactory;
import com.geralt.keycloak.admin.KeycloakUserProvider;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@RequiredArgsConstructor
@PropertySource("classpath:keycloak-admin-cli.properties")
@EnableConfigurationProperties({KeycloakAdminCliProperties.class, KeycloakSpringBootProperties.class})
public class AutoConfigure {

  @Bean
  KeycloakFactory keycloakFactory(KeycloakAdminCliProperties keycloakAdminCliProperties) {
    return new KeycloakFactory(keycloakAdminCliProperties);
  }

  @Bean
  Keycloak keycloak(KeycloakFactory keycloakFactory) {
    return keycloakFactory.createKeycloakAuthorizedBySecret();
  }

  @Bean
  KeycloakUserProvider keycloakUserProvider(Keycloak keycloak, KeycloakSpringBootProperties properties,
    KeycloakAdminCliProperties adminCliProperties) {
    return  new KeycloakUserProvider(keycloak, properties, adminCliProperties);
  }

}
