package com.example.RoadToConcert.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Festival {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "festival_id")
  private Long id;

  private String name;

  @OneToMany(mappedBy = "festival")
  private List<Artist> artistList = new ArrayList<>();
  private String Description;
  private String getTicket;














}
