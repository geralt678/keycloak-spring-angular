package com.geralt.keycloak.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
@KeycloakConfiguration
@EnableGlobalMethodSecurity(jsr250Enabled=true)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(keycloakAuthenticationProvider());
  }

  @ConditionalOnMissingBean
  @Bean
  protected HttpSessionManager httpSessionManager() {
    return new HttpSessionManager();
  }

  @Bean
  public KeycloakSpringBootConfigResolver keycloakSpringBootConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }

  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
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
