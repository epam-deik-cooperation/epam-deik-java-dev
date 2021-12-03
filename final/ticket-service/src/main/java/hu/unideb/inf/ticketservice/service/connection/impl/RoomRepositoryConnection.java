package hu.unideb.inf.ticketservice.service.connection.impl;

import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.repository.RoomRepository;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRoomRepository;

import java.util.List;

public class RoomRepositoryConnection implements ConnectToRoomRepository {

    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;

    public RoomRepositoryConnection(RoomRepository roomRepository, ScreeningRepository screeningRepository) {
        this.roomRepository = roomRepository;
        this.screeningRepository = screeningRepository;
    }

    @Override
    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    @Override
    public void createRoom(Room room) {
        List<Room> rooms = listRooms();
        if (!(rooms.contains(room))) {
            roomRepository.save(room);
        }
    }

    @Override
    public void deleteRoom(String name) {
        List<Screening> screeningList = screeningRepository.findAll();
        for (Screening s : screeningList) {
            if (s.getRoom().getName().equals(name)) {
                screeningRepository.delete(s.getRoom(),s.getMovie(),s.getScreeningDate());
            }
        }
        roomRepository.deleteByName(name);
    }

    @Override
    public void updateRoom(String name, Room room) {
        roomRepository.updateByName(room.getNumberOfSeats(), room.getNumberOfRows(),
                room.getNumberOfColumns(), room.getName());
    }

    @Override
    public void updateComponent(String name, PriceComponent component) {
        roomRepository.updateComponent(name, component);
    }
}
