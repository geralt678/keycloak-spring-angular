package com.geralt.keycloak.security;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty("oec.security.provider.security-enabled")
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
@KeycloakConfiguration
@RequiredArgsConstructor
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

  private final KeycloakClientRequestFactory keycloakClientRequestFactory;

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  KeycloakRestTemplate keycloakRestTemplate() {
    return new KeycloakRestTemplate(keycloakClientRequestFactory);
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(keycloakAuthenticationProvider());
  }

  @ConditionalOnMissingBean
  @Bean
  protected HttpSessionManager httpSessionManager() {
    return new HttpSessionManager();
  }

  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    super.configure(httpSecurity);

    httpSecurity.cors().and()
      .authorizeRequests()
      .antMatchers(HttpMethod.OPTIONS).permitAll()
      .antMatchers(HttpMethod.GET).authenticated()
      .antMatchers(HttpMethod.PUT).authenticated()
      .antMatchers(HttpMethod.POST).authenticated()
      .antMatchers(HttpMethod.DELETE).authenticated();

    httpSecurity.csrf().disable();

    httpSecurity.addFilterBefore(keycloakPreAuthActionsFilter(), LogoutFilter.class)
      .addFilterBefore(keycloakAuthenticationProcessingFilter(), BasicAuthenticationFilter.class)
      .addFilterAfter(keycloakAuthenticatedActionsRequestFilter(), KeycloakAuthenticationProcessingFilter.class)
      .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
  }
}
