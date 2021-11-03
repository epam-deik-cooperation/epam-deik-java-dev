package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    private Room testRoom;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        testRoom = Room.builder()
                .name("test")
                .numberOfColumns(1)
                .numberOfRows(1)
                .build();
    }

    @Test
    public void testGetAllRoomsShouldReturnListOfRooms() {

        //Given
        List<Room> expectedList = List.of(testRoom);

        //When
        when(roomRepository.findAll()).thenReturn(expectedList);
        List<Room> actualList = roomService.getAllRooms();

        //Then
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testCreateRoom() throws AlreadyExistsException {

        //Given

        //When
        roomService.createRoom(testRoom);

        //Then
        verify(roomRepository, times(1)).save(testRoom);
    }

    @Test
    public void testCreateRoomShouldThrowExceptionIfRoomAlreadyExist() throws AlreadyExistsException {

        //Given

        //When
        when(roomRepository.existsByNameContainingIgnoreCase(testRoom.getName())).thenReturn(true);

        //Then
        assertThrows(AlreadyExistsException.class, () -> roomService.createRoom(testRoom));
        verify(roomRepository, times(0)).save(testRoom);

    }

    @Test
    public void testUpdateRoom() throws NotFoundException {

        //Given


        //When
        when(roomRepository.existsByNameContainingIgnoreCase(testRoom.getName())).thenReturn(true);
        roomService.updateRoom(testRoom);

        //Then
        verify(roomRepository, times(1)).update(testRoom.getName(),
                testRoom.getNumberOfColumns(),
                testRoom.getNumberOfRows());
    }

    @Test
    public void testUpdateRoomShouldThrowNotFoundExceptionWhenExistByNameReturnsFalse() {

        //Given


        //When
        when(roomRepository.existsByNameContainingIgnoreCase(testRoom.getName())).thenReturn(false);

        //Then
        assertThrows(NotFoundException.class, () -> roomService.updateRoom(testRoom));
        verify(roomRepository, times(0)).update(testRoom.getName(),
                testRoom.getNumberOfColumns(),
                testRoom.getNumberOfRows());
    }

    @Test
    public void testDeleteRoom() throws NotFoundException {

        //Given


        //When
        when(roomRepository.existsByNameContainingIgnoreCase(testRoom.getName())).thenReturn(true);
        roomService.deleteRoom(testRoom.getName());

        //Then
        verify(roomRepository, times(1)).deleteByNameContainingIgnoreCase(testRoom.getName());
    }

    @Test
    public void testDeleteRoomShouldThrowNotFoundExceptionWhenExistByNameReturnsFalse() {

        //Given


        //When
        when(roomRepository.existsByNameContainingIgnoreCase(testRoom.getName())).thenReturn(false);

        //Then
        assertThrows(NotFoundException.class, () -> roomService.deleteRoom(testRoom.getName()));
        verify(roomRepository, times(0)).deleteByNameContainingIgnoreCase(testRoom.getName());
    }

    @Test
    public void testFindByName() throws NotFoundException {
        // Given

        // When
        when(roomRepository.findByNameContainingIgnoreCase(testRoom.getName())).thenReturn(testRoom);
        roomService.findByName(testRoom.getName());

        // Then
        verify(roomRepository, times(1)).findByNameContainingIgnoreCase(testRoom.getName());
    }
}
