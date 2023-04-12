package com.example.RoadToConcert.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;


@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtistPost {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "artistpost_id")
  private Long id;
  private String content;
  @OneToMany
  private List<File> files = new ArrayList<>();
  @JoinColumn(name = "artist_id" )
  @ManyToOne(fetch = FetchType.LAZY)
  private Artist artist;






}
