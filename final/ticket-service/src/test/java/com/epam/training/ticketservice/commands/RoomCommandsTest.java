package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.businesslogic.account.AccountService;
import com.epam.training.ticketservice.businesslogic.room.RoomService;
import com.epam.training.ticketservice.models.room.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class RoomCommandsTest {

    RoomService roomService;
    AccountService accountService;
    RoomCommands underTest;

    @BeforeEach
    public void init() {
        roomService = Mockito.mock(RoomService.class);
        accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommands(roomService, accountService);
    }

    @Test
    public void stringRepresentationOfRoomShouldDisplayCorrectDetailsOfRoom(){
        var list = List.of(new Room("roomName", 42, 10));
        Mockito.when(roomService.listRooms()).thenReturn(list);
        String returnValue = underTest.listRooms();
        Assertions.assertEquals("Room roomName with 420 seats, 42 rows and 10 columns"
                + System.lineSeparator(), returnValue);
    }

    @Test
    public void createRoomShouldCallCorrectServiceMethod(){
        underTest.createRoom("roomName", 42, 10);
        Mockito.verify(roomService).createRoom("roomName", 42, 10);
    }

    @Test
    public void updateRoomShouldCallCorrectServiceMethod(){
        underTest.updateRoom("roomName", 42, 10);
        Mockito.verify(roomService).updateRoom("roomName", 42, 10);
    }

    @Test
    public void deleteRoomShouldCallCorrectServiceMethod(){
        underTest.deleteRoom("roomName");
        Mockito.verify(roomService).deleteRoom("roomName");
    }
}