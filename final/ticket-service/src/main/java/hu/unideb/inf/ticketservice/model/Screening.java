package hu.unideb.inf.ticketservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import java.util.Objects;

@Entity
public class Screening {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    private Movie movie;
    @OneToOne(cascade = CascadeType.MERGE)
    private Room room;
    private String screeningDate;

    protected Screening() {

    }

    public Screening(Movie movie, Room room, String screeningDate) {
        this.movie = movie;
        this.room = room;
        this.screeningDate = screeningDate;
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
        return Objects.equals(movie, screening.movie) && Objects.equals(room, screening.room)
                && Objects.equals(screeningDate, screening.screeningDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, room, screeningDate);
    }
}
