package com.example.RoadToConcert.domain;

import java.util.Map;

public class KakaoOauth extends Oauth2UserInfo {

  private Integer id;

  public KakaoOauth(Map<String, Object> attributes) {
    super((Map<String, Object>) attributes.get("kakao_account"));
    this.id = (Integer) attributes.get("id");
  }

  @Override
  public String getOauth2Id() {
    return this.id.toString();
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getName() {
    return(String) ( (Map<String, Object>) attributes.get("profile")).get("nickname");
  }
}
