export class KeycloakOptions {
  realm: string;
  url: string;
  clientId: string;
}

export class EnvironmentConfiguration {
  apiUrl: string;
  appUrl: string;
  keycloak: KeycloakOptions;
}

