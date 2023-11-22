package com.epam.training.ticketservice.core.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Seat {
    private int chairRowSeat;
    private int chairColumnSeat;

    @Override
    public String toString() {
        return "(" + chairRowSeat + "," + chairColumnSeat + ")";
    }

    public Seat() {}

    public Seat(int chairRowSeat, int chairColumnSeat) {
        this.chairRowSeat = chairRowSeat;
        this.chairColumnSeat = chairColumnSeat;
    }
}
