import { EnvironmentConfiguration } from './environment.type';

export const environment: EnvironmentConfiguration = {
  production: true,
  appUrl: 'http://localhost:4200/',
  apiUrl: 'http://localhost:9000',
  keycloak: {
    url: '',
    realm: '',
    clientId: ''
  }
};
