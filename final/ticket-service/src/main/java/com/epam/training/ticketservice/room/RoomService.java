package com.epam.training.ticketservice.room;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private final static String ROOM_NOT_FOUND = "No room found with such name!";

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void createRoom(Room newRoom) {
        roomRepository.save(newRoom);
    }

    public void updateRoom(Room roomToUpdate) throws NotFoundException {
        if (roomRepository.existsByName(roomToUpdate.getName())) {
            roomRepository.update(roomToUpdate.getName(), roomToUpdate.getNumberOfColumns(), roomToUpdate.getNumberOfRows());
        } else {
            throw new NotFoundException(ROOM_NOT_FOUND);
        }
    }

    public void deleteRoom(String roomName) throws NotFoundException {
        if (roomRepository.existsByName(roomName)) {
            roomRepository.deleteByName(roomName);
        } else {
            throw new NotFoundException(ROOM_NOT_FOUND);
        }
    }

    public Room findByName(String name) {
        return roomRepository.findByName(name);
    }


}
