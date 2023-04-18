package com.example.RoadToConcert.oauth2;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public abstract class Oauth2UserInfo {

  protected Map<String, Object> attributes;

  public abstract String getOauth2Id();
  public abstract String getEmail();
  public abstract String getName();


}
