package hu.unideb.inf.ticketservice.model;

import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.model.component.DefaultComponent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import java.util.Objects;

@Entity
public class Screening {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Room room;
    private String screeningDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private PriceComponent component;

    protected Screening() {

    }

    public Screening(Movie movie, Room room, String screeningDate) {
        this.movie = movie;
        this.room = room;
        this.screeningDate = screeningDate;
        component = new DefaultComponent();
    }

    public void setComponent(PriceComponent component) {
        this.component = component;
    }

    public PriceComponent getComponent() {
        return component;
    }

    public Movie getMovie() {
        return movie;
    }

    public Room getRoom() {
        return room;
    }

    public String getScreeningDate() {
        return screeningDate;
    }

    @Override
    public String toString() {
        return movie + ", screened in room " + room.getName()
                + ", at " + screeningDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Screening screening = (Screening) o;
        return Objects.equals(component, screening.component) && Objects.equals(movie, screening.movie)
                && Objects.equals(room, screening.room)
                && Objects.equals(screeningDate, screening.screeningDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, room, screeningDate, component);
    }
}
