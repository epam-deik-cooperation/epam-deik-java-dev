package hu.unideb.inf.ticketservice.model;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Transactional
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer numberOfSeats;
    private Integer numberOfRows;
    private Integer numberOfColumns;

    protected Room() {

    }

    public Room(String name, Integer numberOfRows, Integer numberOfColumns) {
        this.name = name;
        this.numberOfSeats = numberOfRows * numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    public String getName() {
        return name;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public Integer getNumberOfColumns() {
        return numberOfColumns;
    }

    @Override
    public String toString() {
        return "Room " + name + " with " + numberOfSeats + " seats, " + numberOfRows
                + " rows and " + numberOfColumns + " columns";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberOfSeats, numberOfRows, numberOfColumns);
    }
}
