package com.epam.training.ticketservice.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String roomName;

    private int chairRow;
    private int chairCol;

    public Room(String roomName, int chairRow, int chairCol) {
        this.roomName = roomName;
        this.chairRow = chairRow;
        this.chairCol = chairCol;
    }
}
