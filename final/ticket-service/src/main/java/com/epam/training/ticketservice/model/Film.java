package com.epam.training.ticketservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name="films")
public class Film {
    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String genre;
    private int length;

    public Film(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }
}
