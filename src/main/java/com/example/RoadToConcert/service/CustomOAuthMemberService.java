package com.example.RoadToConcert.service;

import com.example.RoadToConcert.domain.AuthProvider;
import com.example.RoadToConcert.domain.Member;
import com.example.RoadToConcert.domain.OAuth2UserInfoFrom;
import com.example.RoadToConcert.domain.Oauth2UserInfo;
import com.example.RoadToConcert.domain.Role;
import com.example.RoadToConcert.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
@RequiredArgsConstructor
public class CustomOAuthMemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


  private final MemberRepository memberRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    OAuth2UserService<OAuth2UserRequest, OAuth2User> defaultOAuth2UserService = new DefaultOAuth2UserService();

    OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

    return null;

  }

  protected OAuth2User getOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

    AuthProvider authProvider = AuthProvider.valueOf(
        oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());

    Oauth2UserInfo oAuth2Info = OAuth2UserInfoFrom.getOAuth2Info(authProvider,
        oAuth2User.getAttributes());

    if (!StringUtils.hasText(oAuth2Info.getEmail())) {
      throw new RuntimeException("email isn't provided");
    }

    Member member = memberRepository.findByEmail(oAuth2Info.getEmail()).orElse(null);

    //이미 멤버가 존재
    if (member != null) {
      if (!member.getAuthProvider().equals(authProvider)) {
        throw new RuntimeException("Email already signed up");
      }
      member = updateMember(member, oAuth2Info);
    }else{
      member = registerUser(authProvider, oAuth2Info);
    }

    return null;

  }
  private Member registerUser(AuthProvider authProvider, Oauth2UserInfo oAuth2UserInfo) {
    Member member = Member.builder()
        .email(oAuth2UserInfo.getEmail())
        .name(oAuth2UserInfo.getName())
        .oAuthId(oAuth2UserInfo.getOauth2Id())
        .authProvider(authProvider)
        .role(Role.USER)
        .build();
    return memberRepository.save(member);
  }


  private Member updateMember(Member member, Oauth2UserInfo oAuth2Info) {
    return memberRepository.save(member.update(oAuth2Info));

  }


}
