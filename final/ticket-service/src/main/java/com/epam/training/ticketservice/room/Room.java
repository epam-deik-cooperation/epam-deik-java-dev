package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "number_of_columns")
    private Integer numberOfColumns;

    @Column(name = "number_of_rows")
    private Integer numberOfRows;

    @OneToMany(mappedBy = "room", cascade = {CascadeType.REMOVE})
    private List<Screening> screenings;

    @Override
    public String toString() {
        return "Room " + name + " with " +
                numberOfColumns * numberOfRows + " seats, " +
                numberOfRows + " rows and " + numberOfColumns + " columns";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return name.equals(room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
