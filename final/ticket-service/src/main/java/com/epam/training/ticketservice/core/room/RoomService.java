package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<RoomDto> getRoomList();

    void createRoom(RoomDto room);

    boolean deleteRoom(RoomDto room);

    Optional<RoomDto> getRoomByName(String name);
}
