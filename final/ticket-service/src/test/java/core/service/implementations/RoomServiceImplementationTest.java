package core.service.implementations;

import com.epam.training.ticketservice.core.dto.RoomDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.model.Movie;
import com.epam.training.ticketservice.core.model.Room;
import com.epam.training.ticketservice.core.repository.RoomRepository;
import com.epam.training.ticketservice.core.service.implementations.RoomServiceImplementation;
import com.epam.training.ticketservice.core.service.interfaces.RoomServiceInterface;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoomServiceImplementationTest {
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomServiceInterface underTest = new RoomServiceImplementation(roomRepository);
    Room room = new Room("Apollo", 10, 10);
    Room roomUpdated = new Room("Apollo", 8, 8);

    @Test
    void testRoomCreateShouldSaveRoomWhenRoomDoesIsNotExisting() throws AlreadyExists {
        // Given
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.empty());

        // When
        underTest.roomCreate(room.getRoomName(), room.getChairRow(), room.getChairCol());

        // Then
        verify(roomRepository).save(any(Room.class));
    }

    @Test
    void testRoomCreateShouldNotStoreTheGivenRoomWhenTheInputRoomAlreadyExists() {
        //Given
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));

        // When/Then
        assertThrows(AlreadyExists.class, () -> underTest.roomCreate(room.getRoomName(),
                room.getChairRow(), room.getChairCol()));
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void testRoomUpdateShouldUpdateTheGivenRoomWhenTheStoredRoomIsExisting() throws DoesNotExists {
        // Given
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(roomRepository.save(room)).thenReturn(room);

        // When
        underTest.roomUpdate(room.getRoomName(), roomUpdated.getChairRow(), roomUpdated.getChairCol());

        // Then
        verify(roomRepository).save(room);
    }

    @Test
    void testRoomUpdateShouldNotUpdateTheGivenRoomWhenTheStoredRoomIsNotExisting() {
        // Given
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.empty());

        // When/Then
        assertThrows(DoesNotExists.class, () -> underTest.roomUpdate(room.getRoomName(),
                roomUpdated.getChairRow(), roomUpdated.getChairCol()));
        verify(roomRepository, never()).save(room);
    }

    @Test
    void testRoomDeleteShouldDeleteTheGivenRoomWhenTheStoredRoomIsExisting() throws DoesNotExists {
        // Given
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        doNothing().when(roomRepository).delete(room);

        // When
        underTest.roomDelete(room.getRoomName());

        // Then
        verify(roomRepository, never()).save(room);
        verify(roomRepository).delete(room);
    }

    @Test
    void testRoomDeleteShouldNotDeleteTheGivenRoomWhenTheStoredRoomIsNotExisting() {
        // Given
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.empty());

        // When/Then
        assertThrows(DoesNotExists.class, () -> underTest.roomDelete(room.getRoomName()));
        verify(roomRepository, never()).save(room);
        verify(roomRepository, never()).delete(room);
    }

    @Test
    void testRoomListShouldReturnRoomListWhenARoomIsSaved() {
        // Given
        when(roomRepository.findAll()).thenReturn(Collections.singletonList(room));

        // When
        List<RoomDto> actual = underTest.roomList();

        // Then
        verify(roomRepository).findAll();
        assertEquals(1, actual.size());
        assertEquals(room.getRoomName(), actual.get(0).getRoomName());
        assertEquals(room.getChairRow(), actual.get(0).getChairRow());
        assertEquals(room.getChairCol(), actual.get(0).getChairCol());
    }

    @Test
    void testRoomListShouldReturnEmptyListWhenRoomListIsEmpty() {
        // Given

        // When
        List<RoomDto> actual = underTest.roomList();

        // Then
        assertEquals(emptyList(), actual);
    }
}