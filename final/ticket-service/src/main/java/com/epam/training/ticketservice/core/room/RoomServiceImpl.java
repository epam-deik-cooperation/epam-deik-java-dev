package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.entity.Room;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public List<RoomDto> getRoomList() {
        return roomRepository.findAll().stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public void createRoom(RoomDto roomDto) {
        Room room = new Room(roomDto.getName(), roomDto.getRows(), roomDto.getColumns());
        roomRepository.save(room);
    }

    @Override
    public boolean deleteRoom(RoomDto roomDto) {
        Optional<Room> room = roomRepository.findByName(roomDto.getName());
        if (room.isPresent()) {
            roomRepository.delete(room.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<RoomDto> getRoomByName(String name) {
        return roomRepository.findByName(name).stream().map(this::convert).findFirst();
    }

    private RoomDto convert(Room room) {
        var name = room.getName();
        var rows = room.getRows();
        var columns = room.getColumns();
        return RoomDto.builder().name(name).rows(rows).columns(columns).build();
    }
}
