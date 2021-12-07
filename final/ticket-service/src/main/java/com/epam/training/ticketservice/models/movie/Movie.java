package com.epam.training.ticketservice.models.movie;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;


@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String genre;
    private int minutes;

    public Movie(String name, String genre, int minutes) {
        this.name = name;
        this.genre = genre;
        this.minutes = minutes;
    }

    public void update(String name, String genre, int minutes) {
        this.name = name;
        this.genre = genre;
        this.minutes = minutes;
    }
}
