package com.example.RoadToConcert;

import com.example.RoadToConcert.service.CustomOAuthMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  public final CustomOAuthMemberService customOAuthMemberService;


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

    httpSecurity
        .cors()
        .and()
        .httpBasic().disable()
        .csrf().disable()
        .formLogin().disable()
        .rememberMe().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    httpSecurity.authorizeRequests()
        .antMatchers("/oauth2/**", "/login/**", "/", "/signup/**").permitAll()
        .anyRequest().authenticated();

    httpSecurity.oauth2Login()
        .authorizationEndpoint().baseUri("/oauth2/authorize")
//        .authorizationRequestRepository()
        .and()
        .redirectionEndpoint().baseUri("/oauth2/callback/*")
        .and()
        .userInfoEndpoint().userService(customOAuthMemberService);
//        .and()
//        .successHandler()
//        .failureHandler();

    httpSecurity.logout()
        .clearAuthentication(true)
        .deleteCookies("JESSIONID");


//    httpSecurity.addFilterBefore()

    return httpSecurity.build();
  }


  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }


//  @Bean
//  public AuthenticationManager authenticationManager(
//      AuthenticationConfiguration authenticationConfiguration) throws Exception
//  {
//    return authenticationConfiguration.getAuthenticationManager();
//  }

//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }

//  @Bean
//  public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
//    return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
//  }


}
