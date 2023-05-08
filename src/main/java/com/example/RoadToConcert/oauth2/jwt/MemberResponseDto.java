package com.example.RoadToConcert.oauth2.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberResponseDto {

  @Builder
  @Getter
  @AllArgsConstructor
  public static class TokenInfo{

    private String grantType;
    private String accessToken;

    private Long accessTokenExpirationTime;

    private String refreshToken;

    private Long refreshTokenExpirationTime;


  }

}
