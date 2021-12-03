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
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer numberOfSeats;
    private Integer numberOfRows;
    private Integer numberOfColumns;

    @ManyToOne(cascade = CascadeType.ALL)
    private PriceComponent component;

    protected Room() {

    }

    public Room(String name, Integer numberOfRows, Integer numberOfColumns) {
        this.name = name;
        this.numberOfSeats = numberOfRows * numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        component = new DefaultComponent();
    }

    public PriceComponent getComponent() {
        return component;
    }

    public void setComponent(PriceComponent component) {
        this.component = component;
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
        return Objects.equals(name, room.name) && Objects.equals(numberOfSeats, room.numberOfSeats)
                && Objects.equals(numberOfColumns, room.numberOfColumns)
                && Objects.equals(numberOfRows, room.numberOfRows)
                && Objects.equals(component, room.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberOfSeats, numberOfRows, numberOfColumns, component);
    }
}
