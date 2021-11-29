package hu.unideb.inf.ticketservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Seat {

    @Id
    @GeneratedValue
    private Long id;

    private final Integer row;
    private final Integer column;

    public Seat(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row.equals(seat.row) && column.equals(seat.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
