package com.example.RoadToConcert.domain;


import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Venue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "venue_id")
  private Long id;

  private String name;

  @OneToMany(mappedBy = "venue" , cascade = CascadeType.ALL)
  private List<Event> eventList;

  private String location;



}
