package com.epam.training.ticketservice.core.room.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Builder
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class RoomDto {

    private final String name;
    private int rows;
    private int columns;

    @Override
    public String toString() {
        return "Room " + name + " with " + rows * columns + " seats, " + rows + " rows and " + columns + " columns";
    }
}
