package com.epam.training.ticketservice.businesslogic.room;

import com.epam.training.ticketservice.models.room.Room;
import com.epam.training.ticketservice.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class RoomServiceTest {
    RoomService underTest;
    RoomRepository roomRepository;

    @BeforeEach
    public void init() {
        roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new RoomService(roomRepository);
    }

    @Test
    public void createRoomShouldCallRepository(){
        underTest.createRoom("roomName", 42, 10);
        Mockito.verify(roomRepository).save(new Room("roomName", 42, 10));
    }

    @Test
    public void updateRoomShouldCallRepository(){
        Mockito.when(roomRepository.findByName("roomName")).thenReturn(Optional.of(new Room("roomName", 42, 10)));
        underTest.updateRoom("roomName", 42, 10);
        Mockito.verify(roomRepository).save(new Room("roomName", 42, 10));
    }

    @Test
    public void deleteRoomShouldCallRepository(){
        Mockito.when(roomRepository.findByName("roomName")).thenReturn(Optional.of(new Room("roomName", 42, 10)));
        underTest.deleteRoom("roomName");
        Mockito.verify(roomRepository).delete(new Room("roomName", 42, 10));
    }

}