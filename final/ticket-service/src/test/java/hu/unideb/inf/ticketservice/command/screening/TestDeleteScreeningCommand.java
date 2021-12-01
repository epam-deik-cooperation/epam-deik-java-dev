package hu.unideb.inf.ticketservice.command.screening;

import hu.unideb.inf.ticketservice.command.impl.screening.DeleteScreeningCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
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

import java.util.List;

public class TestDeleteScreeningCommand {

    private final String DEFAULT_DATE = "2021-10-10 10:00";
    private final List<String> PARAMETER_LIST = List.of("Movie","Room",DEFAULT_DATE);
    private final Movie DEFAULT_MOVIE = new Movie("Movie", "genre", 100);
    private final Room DEFAULT_ROOM = new Room("Room", 10,10);

    private DeleteScreeningCommand underTest;
    private LoggedInUserTrackImpl userService;
    
    @Mock
    private ScreeningRepository screeningRepository;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        userService = new LoggedInUserTrackImpl(new DefaultUser());
        underTest = new DeleteScreeningCommand(new ScreeningRepositoryConnection(screeningRepository),userService);
        userService.updateCurrentUser(new Administrator(new AdminCredentialsProvider()));
    }

    @Test
    public void testExecuteShouldDeleteScreeningWhenSignedIntoAPrivilegedAccountGivenTheRightInput()
    {
        //Given
        final Screening screening = new Screening(DEFAULT_MOVIE,DEFAULT_ROOM,DEFAULT_DATE);
        BDDMockito.given(screeningRepository.findAll()).willReturn(List.of(screening));
        final String expected = "Alright";

        //When
        final String result = underTest.execute(PARAMETER_LIST);

        //Then
        Assertions.assertEquals(expected,result);
        Mockito.verify(screeningRepository).delete(DEFAULT_ROOM,DEFAULT_MOVIE,DEFAULT_DATE);
    }

    @Test
    public void testExecuteShouldNotDeleteScreeningWhenGivenNonValidDate()
    {
        //Given
        final String date = "2021-10-10 10:10";
        final Screening screening = new Screening(DEFAULT_MOVIE,DEFAULT_ROOM,DEFAULT_DATE);
        BDDMockito.given(screeningRepository.findAll()).willReturn(List.of(screening));
        final String expected = "There is no screening with movie " + DEFAULT_MOVIE.getName()
                + " inside room " + DEFAULT_ROOM.getName() + " at " + date;

        //When
        final String result = underTest.execute(List.of("Movie","Room","2021-10-10 10:10"));

        //Then
        Assertions.assertEquals(expected,result);
    }
    @Test
    public void testExecuteShouldNotDeleteScreeningWhenGivenNonValidRoomName()
    {
        //Given
        final String roomName = "RoomName";
        final Screening screening = new Screening(DEFAULT_MOVIE,DEFAULT_ROOM,DEFAULT_DATE);
        BDDMockito.given(screeningRepository.findAll()).willReturn(List.of(screening));
        final String expected = "There is no screening with movie " + DEFAULT_MOVIE.getName()
                + " inside room " + roomName + " at " + DEFAULT_DATE;

        //When
        final String result = underTest.execute(List.of("Movie",roomName,"2021-10-10 10:00"));

        //Then
        Assertions.assertEquals(expected,result);
    }
    @Test
    public void testExecuteShouldNotDeleteScreeningWhenGivenNonValidMovieName()
    {
        //Given
        final String movieName = "MovieName";
        final Screening screening = new Screening(DEFAULT_MOVIE,DEFAULT_ROOM,DEFAULT_DATE);
        BDDMockito.given(screeningRepository.findAll()).willReturn(List.of(screening));
        final String expected = "There is no screening with movie " + movieName
                + " inside room " + DEFAULT_ROOM.getName() + " at " + DEFAULT_DATE;

        //When
        final String result = underTest.execute(List.of(movieName,"Room","2021-10-10 10:00"));

        //Then
        Assertions.assertEquals(expected,result);
    }
    @Test
    public void testExecuteShouldNotDeleteScreeningWhenSignedIntoNonPrivilegedAccount()
    {
        //Given
        userService.updateCurrentUser(new DefaultUser());
        final String expected = "Unauthorized action!";

        //When
        final String result = underTest.execute(PARAMETER_LIST);

        //Then
        Assertions.assertEquals(expected,result);
    }

}
