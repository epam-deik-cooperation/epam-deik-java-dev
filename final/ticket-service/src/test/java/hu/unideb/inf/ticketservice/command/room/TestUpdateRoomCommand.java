package hu.unideb.inf.ticketservice.command.room;

import hu.unideb.inf.ticketservice.command.impl.room.UpdateRoomCommand;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.model.user.User;
import hu.unideb.inf.ticketservice.repository.RoomRepository;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.service.connection.impl.RoomRepositoryConnection;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class TestUpdateRoomCommand {

    private final AdminCredentialsProvider credentialsProvider = new AdminCredentialsProvider();
    private final User ADMINISTRATOR = new Administrator(credentialsProvider);
    private final List<String> PARAMETER_LIST = List.of("Room","10","10");

    private UpdateRoomCommand underTest;
    private LoggedInUserTrackService userService;

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ScreeningRepository screeningRepository;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        userService = new LoggedInUserTrackImpl(new DefaultUser());
        underTest = new UpdateRoomCommand(userService, new RoomRepositoryConnection(roomRepository,screeningRepository));
    }

    @Test
    public void testExecuteShouldUpdateARoomWhenThereIsAMatchingRoom()
    {
        //Given
        final String expected = "Alright";
        final Room room = new Room("Room",10,10);
        BDDMockito.given(roomRepository.findAll()).willReturn(List.of(room));
        userService.updateCurrentUser(ADMINISTRATOR);

        //When
        final String result = underTest.execute(List.of("Room","10","10"));

        //Then
        Assertions.assertEquals(expected,result);
        Mockito.verify(roomRepository).updateByName(100,10,10, "Room");
    }

    @Test
    public void testExecuteShouldGiveAMessageWhenThereIsNotAMatchingRoom()
    {
        //Given
        final String expected = "No such room like Room";
        BDDMockito.given(roomRepository.findAll()).willReturn(List.of());
        userService.updateCurrentUser(ADMINISTRATOR);

        //When
        final String result = underTest.execute(PARAMETER_LIST);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldGiveAMessageWhenSignedIntoANonPrivilegedAccount()
    {
        //Given
        final String expected = "Unauthorized action!";

        //When
        final String result = underTest.execute(PARAMETER_LIST);

        //Then
        Assertions.assertEquals(expected,result);
    }

}
