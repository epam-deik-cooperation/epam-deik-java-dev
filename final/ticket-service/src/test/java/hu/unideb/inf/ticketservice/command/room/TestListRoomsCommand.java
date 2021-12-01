package hu.unideb.inf.ticketservice.command.room;

import hu.unideb.inf.ticketservice.command.impl.room.ListRoomCommand;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.repository.RoomRepository;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.service.connection.impl.RoomRepositoryConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class TestListRoomsCommand {

    private ListRoomCommand underTest;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ScreeningRepository screeningRepository;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        underTest = new ListRoomCommand(new RoomRepositoryConnection(roomRepository,screeningRepository));
    }

    @Test
    public void testExecuteShouldReturnAListOfRoomsWhenRoomDatabaseIsNotEmpty()
    {
        //Given
        final Room room = new Room("Room",10,10);
        BDDMockito.given(roomRepository.findAll()).willReturn(List.of(room));
        final String expected = room.toString();

        //When
        final String result = underTest.execute(null);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldReturnAMessageWhenRoomDatabaseIsEmpty()
    {
        //Given
        final String expected = "There are no rooms at the moment";

        //When
        final String result = underTest.execute(null);

        //Then
        Assertions.assertEquals(expected,result);
    }

}
