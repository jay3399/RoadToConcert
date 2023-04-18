package com.example.RoadToConcert.domain;


import com.example.RoadToConcert.oauth2.AuthProvider;
import com.example.RoadToConcert.oauth2.Oauth2UserInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;
  @Column(unique = true, nullable = false)
  private String oAuthId;
  private Long userId;
  private String name;

  @Column(unique = true)
  private String email;
  private String location;

  @OneToMany(mappedBy = "member")
  private List<Follow> follows = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<Bookmark> bookmarks = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<Review> reviews = new ArrayList<>();


  @Enumerated(value = EnumType.STRING)
  private Role role;

  @Enumerated(value = EnumType.STRING)
  private AuthProvider authProvider;


  public Member update(Oauth2UserInfo oauth2UserInfo) {
    this.name = oauth2UserInfo.getName();
    this.oAuthId = oauth2UserInfo.getOauth2Id();

    return this;
  }




}
