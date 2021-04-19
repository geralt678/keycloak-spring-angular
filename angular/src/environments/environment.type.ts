export class KeycloakOptions {
  realm: string;
  url: string;
  clientId: string;
}

export class EnvironmentConfiguration {
  production: boolean;
  apiUrl: string;
  appUrl: string;
  keycloak: KeycloakOptions;
}

