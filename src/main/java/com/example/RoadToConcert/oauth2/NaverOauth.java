package com.example.RoadToConcert.oauth2;

import com.example.RoadToConcert.oauth2.Oauth2UserInfo;
import java.util.Map;

public class NaverOauth extends Oauth2UserInfo {

  public NaverOauth(Map<String, Object> attributes) {
    super((Map<String, Object>) attributes.get("response"));
  }

  @Override
  public String getOauth2Id() {
    return (String) attributes.get("id");
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }
}
