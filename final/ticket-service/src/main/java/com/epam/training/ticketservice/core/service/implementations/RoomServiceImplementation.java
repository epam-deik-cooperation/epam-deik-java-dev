package com.epam.training.ticketservice.core.service.implementations;

import com.epam.training.ticketservice.core.model.Room;
import com.epam.training.ticketservice.core.repository.RoomRepository;
import com.epam.training.ticketservice.core.service.interfaces.RoomServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class RoomServiceImplementation implements RoomServiceInterface {
    private final RoomRepository roomRepository;

    @Override
    public String roomCreate(String roomName, int chairRow, int chairCol) {
        if (roomRepository.findByRoomName(roomName).isPresent()){
            return "The room is already existed";
        }else {
            Room room = new Room(roomName, chairRow, chairCol);
            roomRepository.save(room);
            return "The room created successfully";
        }
    }

    @Override
    public String roomUpdate(String roomName, int chairRow, int chairCol) {
        if (roomRepository.findByRoomName(roomName).isPresent()){
            Room room = roomRepository.findByRoomName(roomName).get();
            room.setChairRow(chairRow);
            room.setChairCol(chairCol);
            roomRepository.save(room);
            return "The room was updated successfully";
        }else {
            return "The room does not exists";
        }
    }

    @Override
    public String roomDelete(String roomName) {
        if (roomRepository.findByRoomName(roomName).isPresent()){
            Room room = roomRepository.findByRoomName(roomName).get();
            roomRepository.delete(room);
            return "The room was deleted successfully";
        }else {
            return "The room does not exists";
        }
    }

    @Override
    public String roomList() {
        List<Room> rooms = roomRepository.findAll();
        if(!rooms.isEmpty()){
            StringBuilder roomsReturned = new StringBuilder();
            StringJoiner joiner = new StringJoiner("\n");
            for (Room room : rooms) {
                joiner.add(room.toString());
            }
            return  roomsReturned.append(joiner).toString();
        }else {
            return "There are no rooms at the moment";
        }
    }
}
