import { EnvironmentConfiguration } from './environment.type';

export const environment: EnvironmentConfiguration = {
  production: false,
  appUrl: 'http://localhost:4200/',
  apiUrl: 'http://localhost:9000',
  keycloak: {
    url: 'http://localhost:8080/auth/',
    realm: 'Test',
    clientId: 'test-client'
  }
};
