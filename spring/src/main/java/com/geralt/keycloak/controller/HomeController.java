package com.geralt.keycloak.controller;

import com.geralt.keycloak.admin.KeycloakUserProvider;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeController {

  private final KeycloakUserProvider keycloakUserProvider;

  @RolesAllowed({"user"})
  @GetMapping("/user-data")
  public Data userData() {
    return new Data("User data");
  }

  @RolesAllowed({"admin"})
  @GetMapping("/admin-data")
  public Data adminData() {
    return new Data("Admin data");
  }

  @RolesAllowed({"admin", "user"})
  @GetMapping("/user-realm")
  public List<String> getUserRealm() {
    return keycloakUserProvider.getRealmUser().stream().map(UserRepresentation::getId).collect(Collectors.toList());
  }

}
