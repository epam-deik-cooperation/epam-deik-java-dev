package com.epam.training.ticketservice.businesslogic.room;

import com.epam.training.ticketservice.models.room.Room;
import com.epam.training.ticketservice.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoomService {

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void createRoom(String name, int rows, int columns) {
        roomRepository.save(new Room(name, rows, columns));
    }

    @Override
    public void updateRoom(String name, int rows, int columns) {
        var room = getByName(name);
        if (room.isPresent()) {
            room.get().update(name, rows, columns);
            roomRepository.save(room.get());
        }
    }

    @Override
    public void deleteRoom(String name) {
        var room = getByName(name);
        room.ifPresent(value -> roomRepository.delete(value));
    }

    @Override
    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> getByName(String name) {
        return roomRepository.findByName(name);
    }
}