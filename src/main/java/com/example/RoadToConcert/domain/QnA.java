package com.example.RoadToConcert.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class QnA {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


}
