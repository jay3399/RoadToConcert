package com.example.RoadToConcert.service;


import com.example.RoadToConcert.domain.MemberResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Slf4j
@Component
public class TokenProvider {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String AUTHORITIES_KEY = "auth";

  private static final String BEARER_TYPE = "Bearer";

  private static final String TYPE_ACCESS = "access";
  private static final String TYPE_REFRESH = "refresh";

  private final String key;

  public TokenProvider(@Value("${oauth.jwt.secret}") String secretKey) {
//    byte[] decode = Decoders.BASE64.decode(secretKey);
//    this.key = Keys.hmacShaKeyFor(decode);

    this.key = Base64.getEncoder().encodeToString(secretKey.getBytes());

  }

  public MemberResponseDto.TokenInfo generateToken(Authentication authentication) {

    return generateToken(authentication.getName(), authentication.getAuthorities());

  }


  // accessToken , refreshToken
  public MemberResponseDto.TokenInfo generateToken(String name, Collection<? extends GrantedAuthority> author) {

    String authorities = author.stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    Date date = new Date();

    String accessToken = Jwts.builder().setSubject(name)
        .claim(AUTHORITIES_KEY, authorities)
        .claim("type", TYPE_ACCESS)
        .setIssuedAt(date)
        .setExpiration(new Date(date.getTime() + ExpireTime.ACCESS_TOKEN_EXPIRE_TIME))
        .signWith(SignatureAlgorithm.HS256, key)
        .compact();

    String refreshToken = Jwts.builder().claim("type", TYPE_REFRESH)
        .setIssuedAt(date)
        .setExpiration(new Date(date.getTime() + ExpireTime.REFRESH_TOKEN_EXPIRE_TIME))
        .signWith(SignatureAlgorithm.ES256, key)
        .compact();

    return MemberResponseDto.TokenInfo.builder()
        .grantType(BEARER_TYPE)
        .accessToken(accessToken)
        .accessTokenExpirationTime(ExpireTime.ACCESS_TOKEN_EXPIRE_TIME)
        .refreshToken(refreshToken)
        .refreshTokenExpirationTime(ExpireTime.REFRESH_TOKEN_EXPIRE_TIME)
        .build();


  }

  //토큰 복호화

  public Authentication getAuthentication(String accessToken) {

    Claims claims = parseClaims(accessToken);

    if (claims.get(AUTHORITIES_KEY) == null) {
      throw new RuntimeException("권한정보가 없는 토큰");
    }


    //클레임에서 권한정보 가져오기
    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    UserDetails user = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(user, "", authorities);



  }


  //토큰 정보 검증


  public boolean validateToken(String token) {

    try {
      Jwts.parserBuilder().setSigningKey(key).build()
          .parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException exception) {
      log.info("Invalid JWT Token", exception);
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT Token", e);
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT Token");
    } catch (IllegalArgumentException e) {
      log.info("Jwt claims string is empty", e);
    }

    return false;
  }


  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
    }

  public String resolveToken(HttpServletRequest request) {

    String header = request.getHeader(AUTHORIZATION_HEADER);

    if (StringUtils.hasText(header) && header.startsWith(BEARER_TYPE)) {
      return header.substring(7);
    }
    return null;
  }




}
