package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomCommandsTest {

    @Mock
    RoomService roomService;

    @Mock
    RoomRepository roomRepository;

    @InjectMocks
    RoomCommands roomCommands;

    private Room room;
    private String name;
    private int rows;
    private int columns;

    @BeforeEach
    void setUp() {
        name = "test";
        rows = 10;
        columns = 10;
        room = Room.builder()
                .name(name)
                .numberOfRows(rows)
                .numberOfColumns(columns)
                .build();
    }


    @Test
    public void testCreateRoom() throws AlreadyExistsException {

        // Given


        // When
        when(roomService.mapToRoom(name, rows, columns)).thenReturn(room);
        roomCommands.createRoom(name, rows, columns);

        // Then
        verify(roomService, times(1)).createRoom(any(Room.class));
    }

    @Test
    public void testCreateRoomShouldNotSaveRoomIfAlreadyExistsExceptionIsCaught()
            throws AlreadyExistsException {

        // Given


        // When
        when(roomService.mapToRoom(name, rows, columns)).thenReturn(room);
        doThrow(AlreadyExistsException.class).when(roomService).createRoom(any(Room.class));
        roomCommands.createRoom(name, rows, columns);

        // Then
        verify(roomRepository, times(0)).save(any(Room.class));
    }


    @Test
    public void testUpdateRoom() throws NotFoundException {

        // Given


        // When
        when(roomService.mapToRoom(name, rows, columns)).thenReturn(room);
        roomCommands.updateRoom(name, rows, columns);

        // Then
        verify(roomService, times(1)).updateRoom(any(Room.class));
    }

    @Test
    public void testUpdateRoomShouldNotUpdateRoomIfAlreadyExistsExceptionIsCaught()
            throws NotFoundException {

        // Given


        // When
        when(roomService.mapToRoom(name, rows, columns)).thenReturn(room);
        doThrow(NotFoundException.class).when(roomService).updateRoom(any(Room.class));
        roomCommands.updateRoom(name, rows, columns);

        // Then
        verify(roomRepository, times(0)).update(anyString(), anyInt(), anyInt());
    }

    @Test
    public void testDeleteRoom() throws NotFoundException {

        // Given


        // When
        roomCommands.deleteRoom(name);

        // Then
        verify(roomService, times(1)).deleteRoom(name);
    }

    @Test
    public void testDeleteRoomShouldNotDeleteRoomIfNotFoundExceptionIsCaught()
            throws NotFoundException {

        // Given


        // When
        doThrow(NotFoundException.class).when(roomService).deleteRoom(name);
        roomCommands.deleteRoom(name);

        // Then
        verify(roomRepository, times(0)).deleteByNameContainingIgnoreCase(name);
    }

    @Test
    public void testListRoomsShouldReturnExpectedStringIfNoRoomsAreFound() {

        // Given
        String expectedString = "There are no rooms at the moment";

        // When
        when(roomService.getAllRooms()).thenReturn(Collections.emptyList());
        String actualString = roomCommands.listRooms();

        // Then
        assertEquals(expectedString, actualString);
    }

    @Test
    public void testListRoomsShouldReturnStringWithFoundRooms() {

        // Given
        Room testRoom1 = Room.builder()
                .name("test")
                .numberOfColumns(10)
                .numberOfRows(10)
                .build();


        Room testRoom2 = Room.builder()
                .name("test2")
                .numberOfColumns(10)
                .numberOfRows(10)
                .build();

        String expectedString = testRoom1 + "\n" + testRoom2;

        // When
        when(roomService.getAllRooms()).thenReturn(List.of(testRoom1, testRoom2));
        String actualString = roomCommands.listRooms();

        // Then
        assertEquals(expectedString, actualString);
    }


}
