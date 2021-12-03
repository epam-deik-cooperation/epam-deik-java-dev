package hu.unideb.inf.ticketservice.command.price;

import hu.unideb.inf.ticketservice.command.impl.price.AttachPriceComponentToRoomCommand;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.repository.ComponentRepository;
import hu.unideb.inf.ticketservice.repository.RoomRepository;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.impl.ComponentRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.RoomRepositoryConnection;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

public class TestAttachPriceComponentToRoomCommand {

    private final List<String> parameters = List.of("Component","Room");

    private LoggedInUserTrackService userTrackService;
    private AttachPriceComponentToRoomCommand underTest;

    @Mock
    private ComponentRepository componentRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ScreeningRepository screeningRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userTrackService = new LoggedInUserTrackImpl(new Administrator(new AdminCredentialsProvider()));
        underTest = new AttachPriceComponentToRoomCommand(userTrackService,
                new ComponentRepositoryConnection(componentRepository),
                new RoomRepositoryConnection(roomRepository,screeningRepository));
    }

    @Test
    public void testExecuteShouldNotAttachPriceToRoomWhenNotSignedIntoAPrivilegedAccount() {
        //Given
        userTrackService.updateCurrentUser(new DefaultUser());
        final String expected = "Unauthorized action!";

        //When
        final String result = underTest.execute(Collections.emptyList());

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldNotAttachPriceToRoomWhenGivenNonExistingComponent() {
        //Given
        BDDMockito.given(componentRepository.findAll()).willReturn(Collections.emptyList());
        final String expected = "There is no component with name Component";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldNotAttachPriceToRoomWhenGivenNonExistingRoom() {
        //Given
        final List<PriceComponent> components = List.of(new PriceComponent("Component",1500));
        BDDMockito.given(componentRepository.findAll()).willReturn(components);
        BDDMockito.given(roomRepository.findAll()).willReturn(Collections.emptyList());
        final String expected = "There is no room with name Room";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldAttachPriceToRoomWhenGivenValidParameters() {
        //Given
        final PriceComponent priceComponent = new PriceComponent("Component",1500);
        final List<PriceComponent> components = List.of(priceComponent);
        BDDMockito.given(componentRepository.findAll()).willReturn(components);
        final List<Room> rooms = List.of(new Room("Room",10,10));
        BDDMockito.given(roomRepository.findAll()).willReturn(rooms);
        final String expected = "Alright";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
        Mockito.verify(roomRepository).updateComponent("Room",priceComponent);
    }

}
