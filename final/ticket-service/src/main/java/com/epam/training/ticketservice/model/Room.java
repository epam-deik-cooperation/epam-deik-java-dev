package com.epam.training.ticketservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    private int seatColumns;
    private int seatRows;

    public Room(String name, int seatColumns, int seatRows) {
        this.name = name;
        this.seatColumns = seatColumns;
        this.seatRows = seatRows;
    }
}
