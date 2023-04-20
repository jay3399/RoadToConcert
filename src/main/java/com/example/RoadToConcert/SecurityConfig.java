package com.example.RoadToConcert;

import com.example.RoadToConcert.oauth2.CookieRequestRepository;
import com.example.RoadToConcert.oauth2.CustomOAuthMemberService;
import com.example.RoadToConcert.oauth2.jwt.JwtAuthenticationFilter;
import com.example.RoadToConcert.oauth2.jwt.OAuth2FailureHandler;
import com.example.RoadToConcert.oauth2.jwt.OAuth2SuccessHandler;
import com.example.RoadToConcert.oauth2.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  public final CustomOAuthMemberService customOAuthMemberService;

  private final CookieRequestRepository requestRepository;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  private final OAuth2FailureHandler oAuth2FailureHandler;

  private final TokenProvider provider;


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
        .antMatchers("/oauth2/**", "/login/**", "/", "/signup/**" , "/actuator/**").permitAll()
        .anyRequest().authenticated();

    httpSecurity.oauth2Login()
        .authorizationEndpoint().baseUri("/oauth2/authorize")
        .authorizationRequestRepository(requestRepository)
        .and()
        .redirectionEndpoint().baseUri("/oauth2/callback/*")
        .and()
        .userInfoEndpoint().userService(customOAuthMemberService)
        .and()
        .successHandler(oAuth2SuccessHandler)
        .failureHandler(oAuth2FailureHandler);


    httpSecurity.logout()
        .clearAuthentication(true)
        .deleteCookies("JESSIONID");

    httpSecurity.addFilterBefore(new JwtAuthenticationFilter(provider),
        UsernamePasswordAuthenticationFilter.class);

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
