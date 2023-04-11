package com.example.RoadToConcert.domain;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
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



}
