package com.example.RoadToConcert.oauth2.jwt;

import static com.example.RoadToConcert.oauth2.CookieRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import com.example.RoadToConcert.oauth2.CookieRequestRepository;
import com.example.RoadToConcert.oauth2.CookieUtils;
import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;


@Component
@RequiredArgsConstructor
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {


  public final CookieRequestRepository requestRepository;


  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException{

    String targetUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
        .map(Cookie::getValue).orElse("/");

    targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam("error", exception.getLocalizedMessage()).build().toUriString();

    System.out.println("targetUrl = " + targetUrl);

    requestRepository.removeAuthorizationRequestCookies(request, response);

    getRedirectStrategy().sendRedirect(request, response, targetUrl);

  }
}
