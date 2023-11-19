package com.epam.training.ticketservice.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String movieName;
    private String movieGenre;
    private int watchTime;

    public Movie(String movieName, String movieGenre, int watchTime) {
        this.movieName = movieName;
        this.movieGenre = movieGenre;
        this.watchTime = watchTime;
    }
}
