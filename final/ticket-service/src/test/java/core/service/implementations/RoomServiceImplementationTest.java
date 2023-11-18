package core.service.implementations;

import com.epam.training.ticketservice.core.model.Movie;
import com.epam.training.ticketservice.core.model.Room;
import com.epam.training.ticketservice.core.repository.RoomRepository;
import com.epam.training.ticketservice.core.service.implementations.RoomServiceImplementation;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoomServiceImplementationTest {
    Room room = new Room("Apollo", 10, 10);
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomServiceImplementation testRoomServiceImplementation = new RoomServiceImplementation(roomRepository);

    @Test
    void testRoomCreateShouldStoreTheGivenRoomWhenTheInputMovieIsNotExisting(){
        //Given
        when(roomRepository.save(room)).thenReturn(room);

        //When
        String actual = testRoomServiceImplementation.roomCreate(room.getRoomName(), room.getChairRow(),
                room.getChairCol());

        //Then
        assertEquals("The room created successfully", actual);
        verify(roomRepository).save(room);
    }

    @Test
    void testRoomCreateShouldNotStoreTheGivenRoomWhenTheInputMovieIsExisting(){
        //Given
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));

        //When
        String actual = testRoomServiceImplementation.roomCreate(room.getRoomName(), room.getChairRow(),
                room.getChairCol());

        //Then
        assertEquals("The room is already existed", actual);
        verify(roomRepository, never()).save(room);
    }

    @Test
    void testRoomUpdateShouldNotUpdateTheGivenRoomWhenTheStoredRoomIsNotExisting() {
        // Given
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.empty());

        // When
        String actual = testRoomServiceImplementation.roomUpdate(room.getRoomName(),
                8, 8);

        // Then
        assertEquals("The room does not exists", actual);
        verify(roomRepository, never()).save(room);
    }

    @Test
    void testRoomUpdateShouldUpdateTheGivenRoomWhenTheStoredRoomIsExisting() {
        // Given
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(roomRepository.save(room)).thenReturn(room);

        // When
        String actual = testRoomServiceImplementation.roomUpdate(room.getRoomName(),
                8, 8);

        // Then
        assertEquals("The room was updated successfully", actual);
        verify(roomRepository).save(room);
    }

    @Test
    void testRoomDeleteShouldNotDeleteTheGivenRoomWhenTheStoredRoomIsNotExisting() {
        // Given
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.empty());

        // When
        String actual = testRoomServiceImplementation.roomDelete(room.getRoomName());

        // Then
        assertEquals("The room does not exists", actual);
        verify(roomRepository, never()).save(room);
        verify(roomRepository, never()).delete(room);
    }

    @Test
    void testRoomDeleteShouldDeleteTheGivenRoomWhenTheStoredRoomIsExisting() {
        // Given
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        doNothing().when(roomRepository).delete(room);

        // When
        String actual = testRoomServiceImplementation.roomDelete(room.getRoomName());

        // Then
        assertEquals("The room was deleted successfully", actual);
        verify(roomRepository, never()).save(room);
        verify(roomRepository).delete(room);
    }

    @Test
    void testRoomListShouldReturnRoomsListWhenARoomIsSaved() {
        // Given
        List<Room> roomList = Collections.singletonList(room);
        when(roomRepository.findAll()).thenReturn(roomList);

        // When
        String actual = testRoomServiceImplementation.roomList();

        // Then
        assertEquals("Room Apollo with 100 seats, 10 rows and 10 columns", actual);
    }

    @Test
    void testRoomListShouldReturnNoRoomsWhenRoomListIsEmpty() {
        // Given

        // When
        String actual = testRoomServiceImplementation.roomList();

        // Then
        assertEquals("There are no rooms at the moment", actual);
    }
}
