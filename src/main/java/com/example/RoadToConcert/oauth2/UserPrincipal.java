package com.example.RoadToConcert.oauth2;

import com.example.RoadToConcert.domain.Member;
import com.example.RoadToConcert.domain.Role;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;


@Getter
@AllArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails {


  private Long id;

  private String email;

  private Collection<? extends GrantedAuthority> authorities;

  @Setter
  private Map<String, Object> attributes;


  public UserPrincipal(Long id, String email, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.email = email;
    this.authorities = authorities;
  }

  public static UserPrincipal create(Member member, Map<String, Object> attributes) {
    List<GrantedAuthority> authorityList = Collections.singletonList(
        new SimpleGrantedAuthority(Role.USER.name()));

    UserPrincipal userPrincipal = new UserPrincipal(member.getUserId(), member.getEmail(),
        authorityList);

    userPrincipal.setAttributes(attributes);
    return userPrincipal;

  }


  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return null;
  }

  @Override
  public String getName() {
    return String.valueOf(id);
  }
}

