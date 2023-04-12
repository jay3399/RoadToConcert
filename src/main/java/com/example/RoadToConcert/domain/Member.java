package com.example.RoadToConcert.domain;


import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;
  private Long userId;
  private String firstName;
  private String lastName;
  private String email;
  private String location;

  @OneToMany(mappedBy = "member")
  private List<Follow> follows = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<Bookmark> bookmarks = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<Review> reviews = new ArrayList<>();


}
