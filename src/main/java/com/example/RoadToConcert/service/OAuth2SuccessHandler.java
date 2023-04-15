package com.example.RoadToConcert.service;


import static com.example.RoadToConcert.repo.CookieRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import com.example.RoadToConcert.domain.MemberResponseDto;
import com.example.RoadToConcert.repo.CookieRequestRepository;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


  private String redirectUri;
  public final TokenProvider provider;

  private final CookieRequestRepository cookieRequestRepository;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    String targetUrl = determineTargetUrl(request, response, authentication);

    if (response.isCommitted()) {
      log.info("Response has already been committed");
      return;
    }

    clearAuthenticationAttributes(request, response);
    getRedirectStrategy().sendRedirect(request, response, targetUrl);


  }

  @Override
  protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {

    Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
        .map(Cookie::getValue);

    if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
      throw new RuntimeException("redirect Urls are not matched");
    }

    String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

    MemberResponseDto.TokenInfo tokenInfo =
        provider.generateToken(authentication);

    return UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam("token", tokenInfo.getAccessToken())
        .build().toString();
  }


  protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
    super.clearAuthenticationAttributes(request);
    cookieRequestRepository.removeAuthorizationRequestCookies(request, response);
  }

  private boolean isAuthorizedRedirectUri(String uri) {
    URI clientRedirectUri = URI.create(uri);
    URI authorizedUri = URI.create(redirectUri);

    if (authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
        && authorizedUri.getPort() == clientRedirectUri.getPort()) {
      return true;
    }
    return false;
  }




}
