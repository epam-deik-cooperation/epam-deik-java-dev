package hu.unideb.inf.ticketservice.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;
    private Integer price;

    @OneToMany
    private List<Seat> seats;

    public Booking(Screening screening, Integer price, List<Seat> seats) {
        this.screening = screening;
        this.price = price;
        this.seats = seats;
    }

    public Screening getScreening() {
        return screening;
    }

    public Integer getPrice() {
        return price;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return "Seats " + seats + " on " + screening.getMovie().getName()
                + " in room " + screening.getRoom().getName()
                + " starting at " + screening.getScreeningDate()
                + " for " + price + " HUF";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return screening.equals(booking.screening) && price.equals(booking.price) && seats.equals(booking.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(screening, price, seats);
    }
}
