package hu.unideb.inf.ticketservice.command.price;

import hu.unideb.inf.ticketservice.command.impl.price.AttachPriceComponentToScreeningCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.repository.ComponentRepository;
import hu.unideb.inf.ticketservice.repository.MovieRepository;
import hu.unideb.inf.ticketservice.repository.RoomRepository;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.impl.ComponentRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.MovieRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.RoomRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.ScreeningRepositoryConnection;
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

public class TestAttachPriceComponentToScreeningCommand {

    private final List<String> parameters = List.of("Component","Movie","Room","2021-10-10 10:00");
    private AttachPriceComponentToScreeningCommand underTest;
    private LoggedInUserTrackService userTrackService;
    @Mock
    private ComponentRepository componentRepository;
    @Mock
    private ScreeningRepository screeningRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userTrackService = new LoggedInUserTrackImpl(new Administrator(new AdminCredentialsProvider()));
        underTest = new AttachPriceComponentToScreeningCommand(userTrackService,
                new ComponentRepositoryConnection(componentRepository),
                new ScreeningRepositoryConnection(screeningRepository),
                new RoomRepositoryConnection(roomRepository,screeningRepository),
                new MovieRepositoryConnection(movieRepository,screeningRepository));
    }

    @Test
    public void testExecuteShouldNotAttachPriceToScreeningWhenNotSignedIntoAPrivilegedAccount() {
        //Given
        userTrackService.updateCurrentUser(new DefaultUser());
        final String expected = "Unauthorized action!";

        //When
        final String result = underTest.execute(Collections.emptyList());

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldNotAttachPriceToScreeningWhenGivenNonExistingComponent() {
        //Given
        BDDMockito.given(componentRepository.findAll()).willReturn(Collections.emptyList());
        final String expected = "There is no component with name Component";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldNotAttachPriceToScreeningWhenGivenNonExistingMovie() {
        //Given
        final List<PriceComponent> components = List.of(new PriceComponent("Component",1500));
        BDDMockito.given(componentRepository.findAll()).willReturn(components);
        BDDMockito.given(movieRepository.findAll()).willReturn(Collections.emptyList());
        final String expected = "There is no movie with name Movie";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldNotAttachPriceToScreeningWhenGivenNonExistingRoom() {
        //Given
        final List<PriceComponent> components = List.of(new PriceComponent("Component",1500));
        BDDMockito.given(componentRepository.findAll()).willReturn(components);
        final List<Movie> movies = List.of(new Movie("Movie","genre",156));
        BDDMockito.given(movieRepository.findAll()).willReturn(movies);
        BDDMockito.given(roomRepository.findAll()).willReturn(Collections.emptyList());
        final String expected = "There is no room with name Room";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldNotAttachPriceToScreeningWhenGivenNonExistingScreening() {
        //Given
        final List<PriceComponent> components = List.of(new PriceComponent("Component",1500));
        BDDMockito.given(componentRepository.findAll()).willReturn(components);
        final List<Movie> movies = List.of(new Movie("Movie","genre",156));
        BDDMockito.given(movieRepository.findAll()).willReturn(movies);
        final List<Room> rooms = List.of(new Room("Room",10,10));
        BDDMockito.given(roomRepository.findAll()).willReturn(rooms);
        BDDMockito.given(screeningRepository.findAll()).willReturn(Collections.emptyList());
        final String expected = "There is no screening with movie Movie in room Room at 2021-10-10 10:00";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldAttachPriceToScreeningWhenGivenValidParameters() {
        //Given
        final Movie movie = new Movie("Movie","genre",156);
        final Room room = new Room("Room",10,10);
        final String date = "2021-10-10 10:00";
        final Screening screening = new Screening(movie,room,date);
        final  PriceComponent component = new PriceComponent("Component",1500);
        BDDMockito.given(componentRepository.findAll()).willReturn(List.of(component));
        BDDMockito.given(movieRepository.findAll()).willReturn(List.of(movie));
        BDDMockito.given(roomRepository.findAll()).willReturn(List.of(room));
        BDDMockito.given(screeningRepository.findAll()).willReturn(List.of(screening));
        final String expected = "Alright";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
        Mockito.verify(screeningRepository).updateComponent(room,movie,date,component);
    }
}
