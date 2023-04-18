package com.example.RoadToConcert.oauth2;

import java.util.Map;

public class OAuth2UserInfoFrom {

  public static Oauth2UserInfo getOAuth2Info(AuthProvider authProvider,
      Map<String, Object> attributes) {

    switch (authProvider) {
      case KAKAO:
        return new KakaoOauth(attributes);
      case NAVER:
        return new NaverOauth(attributes);
      case GOOGLE:
        return new GoogleOauth(attributes);

      default:throw new IllegalArgumentException("invalid value");
    }


  }


}
