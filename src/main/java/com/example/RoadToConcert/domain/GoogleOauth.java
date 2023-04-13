package com.example.RoadToConcert.domain;

import java.util.Map;

public class GoogleOauth extends Oauth2UserInfo {

  public GoogleOauth(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getOauth2Id() {
    return (String) attributes.get("sub");
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
