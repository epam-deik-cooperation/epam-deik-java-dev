package com.epam.training.ticketservice.core.movie.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Movie {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;
    private String theme;
    private int length;

    public Movie(String name, String theme, int length) {
        this.name = name;
        this.theme = theme;
        this.length = length;
    }
}
