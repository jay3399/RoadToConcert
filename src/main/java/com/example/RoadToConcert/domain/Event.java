package com.example.RoadToConcert.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;


@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "event_id")
  private Long id;
  @ManyToOne
  @JoinColumn(name = "artist_id")
  private Artist artist;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "venue_id")
  private Venue venue;
  private LocalDateTime localDateTime;
  private String Description;
  private String getTicket;


}
