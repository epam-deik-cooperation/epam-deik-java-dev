package hu.unideb.inf.ticketservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Entity
@Transactional
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    private final Screening screening;
    private final Integer price;

    @OneToMany(fetch = FetchType.EAGER)
    private final List<Seat>  seats;

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
        return "Seats " + getSeatsOutputString() + " on " + screening.getMovie().getName()
                + " in room " + screening.getRoom().getName()
                + " starting at " + screening.getScreeningDate()
                + " for " + price + " HUF";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booking booking = (Booking) o;
        return screening.equals(booking.screening) && price.equals(booking.price) && seats.equals(booking.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(screening, price, seats);
    }


    private String getSeatsOutputString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Seat s : getSeats()) {
            stringBuilder.append(s.toString());
            stringBuilder.append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2,stringBuilder.length());
        return stringBuilder.toString();
    }
}
