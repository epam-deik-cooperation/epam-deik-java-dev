package hu.unideb.inf.ticketservice.command.room;

import hu.unideb.inf.ticketservice.command.impl.room.CreateRoomCommand;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRoomRepository;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class TestCreateRoomCommand {

    private final List<String> PARAMETER_LIST = List.of("Room","10","10");

    private AdminCredentialsProvider credentialsProvider;
    private CreateRoomCommand underTest;
    private LoggedInUserTrackImpl userService;
    @Mock
    private ConnectToRoomRepository roomRepository;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        credentialsProvider = new AdminCredentialsProvider();
        userService = new LoggedInUserTrackImpl(new DefaultUser());
        underTest = new CreateRoomCommand(userService, roomRepository);
    }

    @Test
    public void testExecuteShouldNotCreateRoomWhenNotSignedIntoAPrivilegedAccount()
    {
        //Given
        final String expected = "Unauthorized action!";

        //When
        final String result = underTest.execute(PARAMETER_LIST);

        //Then
        Assertions.assertEquals(expected,result);
    }
    @Test
    public void testExecuteShouldCreateRoomWhenSignedIntoAPrivilegedAccountAndGivenValidInput()
    {
        //Given
        final String expected = "Alright";
        userService.updateCurrentUser(new Administrator(credentialsProvider));

        //When
        final String result = underTest.execute(PARAMETER_LIST);

        //Then
        Assertions.assertEquals(expected,result);
        Mockito.verify(roomRepository).createRoom(new Room("Room",10,10));
    }

}
