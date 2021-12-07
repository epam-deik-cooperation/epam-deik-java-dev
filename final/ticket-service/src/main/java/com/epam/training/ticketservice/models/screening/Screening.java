package com.epam.training.ticketservice.models.screening;

import com.epam.training.ticketservice.models.movie.Movie;
import com.epam.training.ticketservice.models.room.Room;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Room room;
    private LocalDateTime dateTime;

    public Screening(Movie movie, Room room, LocalDateTime dateTime) {
        this.movie = movie;
        this.room = room;
        this.dateTime = dateTime;
    }

    public void update(Movie movie, Room room, LocalDateTime dateTime) {
        this.movie = movie;
        this.room = room;
        this.dateTime = dateTime;
    }
}
