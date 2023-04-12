package com.example.RoadToConcert.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Event {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "event_id")
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "artist_id")
  private Artist artist;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "venue_id")
  private Venue venue;


  @OneToMany()
  private List<Review> reviews = new ArrayList<>();

  @OneToMany(mappedBy = "event")
  private List<Bookmark> bookmarks = new ArrayList<>();

  private LocalDateTime localDateTime;
  private String Description;
  private String getTicket;

  public void setVenue(Venue venue) {
    this.venue = venue;
    venue.getEventList().add(this);
  }

  public void setArtist(Artist artist) {
    this.artist = artist;
    artist.getEvents().add(this);
  }





}
