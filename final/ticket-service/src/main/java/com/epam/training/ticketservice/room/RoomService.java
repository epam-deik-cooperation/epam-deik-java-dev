package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private static final String ROOM_NOT_FOUND = "No room found with such name";
    private static final String ROOM_ALREADY_EXIST = "Room already exists with such name";

    private final RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room findByName(String name) throws NotFoundException {

        Room room = roomRepository.findByNameContainingIgnoreCase(name);

        if (room != null) {
            return room;
        } else {
            throw new NotFoundException(ROOM_NOT_FOUND);
        }
    }

    public Room mapToRoom(String name, int rows, int columns) {
        return Room.builder()
                .name(name)
                .numberOfRows(rows)
                .numberOfColumns(columns)
                .build();
    }

    public void createRoom(Room room) throws AlreadyExistsException {
        if (!roomRepository.existsByNameContainingIgnoreCase(room.getName())) {
            roomRepository.save(room);
        } else {
            throw new AlreadyExistsException(ROOM_ALREADY_EXIST);
        }
    }

    public void updateRoom(Room room) throws NotFoundException {
        if (roomRepository.existsByNameContainingIgnoreCase(room.getName())) {
            roomRepository.update(room.getName(),
                    room.getNumberOfColumns(),
                    room.getNumberOfRows());
        } else {
            throw new NotFoundException(ROOM_NOT_FOUND);
        }
    }

    public void deleteRoom(String name) throws NotFoundException {
        if (roomRepository.existsByNameContainingIgnoreCase(name)) {
            roomRepository.deleteByNameContainingIgnoreCase(name);
        } else {
            throw new NotFoundException(ROOM_NOT_FOUND);
        }
    }

}
