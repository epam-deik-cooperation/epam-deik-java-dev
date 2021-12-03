package hu.unideb.inf.ticketservice.service.connection;

import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;

import java.util.List;

public interface ConnectToRoomRepository {

    List<Room> listRooms();

    void createRoom(Room room);

    void deleteRoom(String name);

    void updateRoom(String name, Room room);

    void updateComponent(String name, PriceComponent component);

}
