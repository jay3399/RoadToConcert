package com.example.RoadToConcert.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notice_id")
  private Long id;

  private String contents;
  private Long countVisit;




}
