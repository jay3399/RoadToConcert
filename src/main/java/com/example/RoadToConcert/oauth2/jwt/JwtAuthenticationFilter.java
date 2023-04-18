package com.example.RoadToConcert.oauth2.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

  public final TokenProvider provider;


  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    // 헤더에서 jwt token 추출

    String token = provider.resolveToken((HttpServletRequest) request);

    if (token != null && provider.validateToken(token)) {

      Authentication authentication = provider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    chain.doFilter(request, response);

  }
}
