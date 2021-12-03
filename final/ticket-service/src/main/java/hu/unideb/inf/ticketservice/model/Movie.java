package hu.unideb.inf.ticketservice.model;

import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.model.component.DefaultComponent;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
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

    @ManyToOne(cascade = CascadeType.ALL)
    private PriceComponent component;

    protected Movie() {

    }

    public Movie(String name, String genre, Integer movieLength) {
        this.name = name;
        this.genre = genre;
        this.movieLength = movieLength;
        component = new DefaultComponent();
    }

    public void setComponent(PriceComponent component) {
        this.component = component;
    }

    public PriceComponent getComponent() {
        return component;
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
        return Objects.equals(name, movie.name) && Objects.equals(movieLength, movie.movieLength)
                && Objects.equals(genre, movie.genre) && Objects.equals(component, movie.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre, movieLength, component);
    }
}
