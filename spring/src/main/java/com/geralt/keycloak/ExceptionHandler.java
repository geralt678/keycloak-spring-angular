package com.geralt.keycloak;

import javax.ws.rs.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler({
    ForbiddenException.class
  })
  public ResponseEntity forbidden(RuntimeException exc) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exc.getMessage());
  }

}
