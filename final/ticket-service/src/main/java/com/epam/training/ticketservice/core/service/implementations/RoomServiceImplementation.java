package com.epam.training.ticketservice.core.service.implementations;

import com.epam.training.ticketservice.core.dto.RoomDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.model.Room;
import com.epam.training.ticketservice.core.repository.RoomRepository;
import com.epam.training.ticketservice.core.service.interfaces.RoomServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImplementation implements RoomServiceInterface {
    private final RoomRepository roomRepository;

    @Override
    public void roomCreate(String roomName, int chairRow, int chairCol) throws AlreadyExists {
        if (roomRepository.findByRoomName(roomName).isPresent()) {
            throw new AlreadyExists("The room is already existed");
        } else {
            Room room = new Room(roomName, chairRow, chairCol);
            roomRepository.save(room);
        }
    }

    @Override
    public void roomUpdate(String roomName, int chairRow, int chairCol) throws DoesNotExists {
        if (roomRepository.findByRoomName(roomName).isPresent()) {
            Room room = roomRepository.findByRoomName(roomName).get();
            room.setChairRow(chairRow);
            room.setChairCol(chairCol);
            roomRepository.save(room);
        } else {
            throw new DoesNotExists("The room does not exists");
        }
    }

    @Override
    public void roomDelete(String roomName) throws DoesNotExists {
        if (roomRepository.findByRoomName(roomName).isPresent()) {
            Room room = roomRepository.findByRoomName(roomName).get();
            roomRepository.delete(room);
        } else {
            throw new DoesNotExists("The room does not exists");
        }
    }

    @Override
    public List<RoomDto> roomList() {
        return roomRepository.findAll().stream().map(RoomDto::new).toList();
    }
}
