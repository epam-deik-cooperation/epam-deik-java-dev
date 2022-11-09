package com.epam.training.ticketservice.core.screening.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue
    private Integer id;
    private String movie;
    private String room;
    private LocalDateTime start;
    private LocalDateTime endMovie;

    public Screening(String movie, String room, LocalDateTime start, LocalDateTime endMovie) {
        this.movie = movie;
        this.room = room;
        this.start = start;
        this.endMovie = endMovie;
    }
}
