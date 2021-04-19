package com.geralt.keycloak.controller;

import lombok.Getter;

public class Data {

  @Getter
  private String data;

  public Data(String data) {
    this.data = data;
  }
}
