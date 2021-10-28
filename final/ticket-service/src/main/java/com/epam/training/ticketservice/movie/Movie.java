package com.epam.training.ticketservice.movie;

import com.epam.training.ticketservice.booking.price.PriceComponent;
import com.epam.training.ticketservice.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

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

    @ManyToOne
    private PriceComponent priceComponent;

    @OneToMany(mappedBy = "movie")
    private List<Screening> screenings;

    @Override
    public String toString() {
        return title + " (" + genre + ", " + length + ")";
    }

    @PrePersist
    @PreUpdate
    public void formatTitleAndGenre() {
        this.title = title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();
        this.genre = genre.toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return id.equals(movie.id) && title.equals(movie.title) && Objects.equals(length, movie.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, length, genre);
    }
}
