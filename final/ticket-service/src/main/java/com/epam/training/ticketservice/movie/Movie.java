package com.epam.training.ticketservice.movie;

import com.epam.training.ticketservice.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "length")
    private Integer length;

    @Column(name = "genre")
    private String genre;

    @OneToMany(mappedBy = "movie", cascade = {CascadeType.REMOVE})
    private List<Screening> screenings = new ArrayList<>();

    @Override
    public String toString() {
        return title + " (" + genre + ", " + length + ")";
    }

    @PrePersist
    @PreUpdate
    public void formatTitleAndGenre() {
        this.title = title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase();
        this.genre = genre.toLowerCase();
    }
}
