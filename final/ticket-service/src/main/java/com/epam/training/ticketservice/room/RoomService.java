package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private static final String ROOM_NOT_FOUND = "No room found with such name";
    private static final String ROOM_ALREADY_EXIST = "Room already exists with such name";

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void createRoom(Room newRoom) throws AlreadyExistsException {
        if (!roomRepository.existsByNameContainingIgnoreCase(newRoom.getName())) {
            roomRepository.save(newRoom);
        } else {
            throw new AlreadyExistsException(ROOM_ALREADY_EXIST);
        }
    }

    public void updateRoom(Room roomToUpdate) throws NotFoundException {
        if (roomRepository.existsByNameContainingIgnoreCase(roomToUpdate.getName())) {
            roomRepository.update(roomToUpdate.getName(),
                    roomToUpdate.getNumberOfColumns(),
                    roomToUpdate.getNumberOfRows());
        } else {
            throw new NotFoundException(ROOM_NOT_FOUND);
        }
    }

    public void deleteRoom(String roomName) throws NotFoundException {
        if (roomRepository.existsByNameContainingIgnoreCase(roomName)) {
            roomRepository.deleteByNameContainingIgnoreCase(roomName);
        } else {
            throw new NotFoundException(ROOM_NOT_FOUND);
        }
    }

    public Room findByName(String name) throws NotFoundException {

        Room room = roomRepository.findByNameContainingIgnoreCase(name);

        if (room != null) {
            return room;
        } else {
            throw new NotFoundException(ROOM_NOT_FOUND);
        }
    }


}
