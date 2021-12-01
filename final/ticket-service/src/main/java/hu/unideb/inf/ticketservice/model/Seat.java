package hu.unideb.inf.ticketservice.model;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Objects;

@Entity
public class Seat {

    @Id
    @GeneratedValue
    private Long id;

    private Integer rowNumber;
    private Integer columnNumber;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Room getRoom() {
        return room;
    }

    public Seat(Integer row, Integer column, Room room) {
        this.rowNumber = row;
        this.columnNumber = column;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    @Override
    public String toString() {
        return "(" + rowNumber + "," + columnNumber + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seat seat = (Seat) o;
        return Objects.equals(rowNumber, seat.rowNumber) && Objects.equals(columnNumber, seat.columnNumber)
                && Objects.equals(room, seat.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNumber, columnNumber, room);
    }
}
