package com.epam.training.ticketservice.core.dto;

import com.epam.training.ticketservice.core.model.Room;
import lombok.Getter;

@Getter
public class RoomDto {
    private final String roomName;
    private final int chairRow;
    private final int chairCol;

    public RoomDto(Room room) {
        roomName = room.getRoomName();
        chairRow = room.getChairRow();
        chairCol = room.getChairCol();
    }

    @Override
    public String toString() {
        return "Room " + roomName
                + " with " + (chairRow * chairCol)
                + " seats, " + chairRow
                + " rows and " + chairCol + " columns";
    }
}
