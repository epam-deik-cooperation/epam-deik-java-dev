package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.movie.Movie;

import com.epam.training.ticketservice.room.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "screenings")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;


    @Override
    public String toString() {
        return movie
                + ", screened in room "
                + room.getName() + ", at "
                + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
