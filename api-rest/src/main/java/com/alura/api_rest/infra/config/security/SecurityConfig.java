package com.alura.api_rest.infra.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import static com.alura.api_rest.domain.constants.SecurityFilterConstants.SKIP_FILTER_PATHS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

  private final SecurityFilter securityFilter;
  private final AntPathMatcher antPathMatcher = new AntPathMatcher();


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    final String ADMIN = "ADMIN";
    return httpSecurity
          .csrf(AbstractHttpConfigurer::disable)
          .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(req -> {
            req.requestMatchers(SKIP_FILTER_PATHS).permitAll();
            req.requestMatchers(HttpMethod.GET, "/patients").hasRole(ADMIN);
            req.anyRequest().permitAll();
          }).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
          .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
