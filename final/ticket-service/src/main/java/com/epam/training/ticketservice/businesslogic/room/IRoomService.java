package com.epam.training.ticketservice.businesslogic.room;

import com.epam.training.ticketservice.models.room.Room;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    void createRoom(String name, int rows, int columns);

    void updateRoom(String name, int rows, int columns);

    void deleteRoom(String name);

    List<Room> listRooms();

    Optional<Room> getByName(String name);
}