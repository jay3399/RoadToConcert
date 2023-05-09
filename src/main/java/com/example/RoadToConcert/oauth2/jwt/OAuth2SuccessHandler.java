package com.example.RoadToConcert.oauth2.jwt;
import static com.example.RoadToConcert.oauth2.CookieRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import com.example.RoadToConcert.oauth2.CookieRequestRepository;
import com.example.RoadToConcert.oauth2.CookieUtils;
import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


  @Value("${oauth.oauth2.authorizedRedirectUri}")
  private String redirectUri;
  private final TokenProvider provider;
  private final CookieRequestRepository cookieRequestRepository;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException{

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

    System.out.println("redirectUri = " + redirectUri);

    if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
      throw new RuntimeException("redirect Urls are not matched");
    }

    String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

    String name = authentication.getName();

    MemberResponseDto.TokenInfo tokenInfo = provider.generateToken(authentication);

    Enumeration<String> headerNames = request.getHeaderNames();

    Iterator<String> stringIterator = headerNames.asIterator();

    for (Iterator<String> it = stringIterator; it.hasNext(); ) {
      Object o = it.next();
    }

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
