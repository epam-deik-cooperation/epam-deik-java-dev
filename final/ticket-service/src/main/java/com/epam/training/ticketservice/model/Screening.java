package com.epam.training.ticketservice.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name="screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate startTime;
    private String filmTitle;
    private int roomName;

    public Screening(LocalDate startTime, String filmTitle, int roomName) {
        this.startTime = startTime;
        this.filmTitle = filmTitle;
        this.roomName = roomName;
    }
}
