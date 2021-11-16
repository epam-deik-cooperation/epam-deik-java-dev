package com.epam.training.ticketservice.booking.price;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private List<Room> rooms;

    @OneToMany
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private List<Movie> movies;

    @OneToMany
    @JoinColumn(name = "screening_id", referencedColumnName = "id")
    private List<Screening> screenings;

    @Column(name = "price")
    private int price;

    @PrePersist
    @PreUpdate
    public void formatName() {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

}
