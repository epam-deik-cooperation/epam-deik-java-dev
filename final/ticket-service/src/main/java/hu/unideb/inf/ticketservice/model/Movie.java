package hu.unideb.inf.ticketservice.model;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Transactional
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String genre;
    private Integer movieLength;

    protected Movie() {

    }

    public Movie(String name, String genre, Integer movieLength) {
        this.name = name;
        this.genre = genre;
        this.movieLength = movieLength;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getNumberOfMinutes() {
        return movieLength;
    }

    @Override
    public String toString() {
        return name + " (" + genre + ", " + movieLength + " minutes)";
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
        return Objects.equals(name, movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre, movieLength);
    }
}
