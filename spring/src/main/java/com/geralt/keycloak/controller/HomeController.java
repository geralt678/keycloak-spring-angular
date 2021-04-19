package com.geralt.keycloak.controller;



import javax.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class HomeController {

  @RolesAllowed("user")
  @GetMapping("/user-data")
  public Data userData() {
    return new Data("User data");
  }

  @RolesAllowed("admin")
  @GetMapping("/admin-data")
  public Data adminData() {
    return new Data("Admin data");
  }

}
