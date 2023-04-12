package com.example.RoadToConcert.domain;

import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long id;
    private String name;
    private String Description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @OneToMany(mappedBy = "artist")
    private List<ArtistPost> artistPostList = new ArrayList<>();
    @OneToMany(mappedBy = "artist")
    private List<Event> events = new ArrayList<>();
    @OneToMany(mappedBy = "artist")
    private List<Follow> follows = new ArrayList<>();


    public void setFestival(Festival festival) {
        this.festival = festival;
        festival.getArtistList().add(this);
    }






}
