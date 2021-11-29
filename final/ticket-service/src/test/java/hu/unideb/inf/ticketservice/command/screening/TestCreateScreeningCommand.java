package hu.unideb.inf.ticketservice.command.screening;

import hu.unideb.inf.ticketservice.command.impl.screening.CreateScreeningCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.connection.ConnectToMovieRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRoomRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToScreeningRepository;
import hu.unideb.inf.ticketservice.service.impl.DateValidationImpl;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class TestCreateScreeningCommand {

    private final String DEFAULT_DATE = "2021-10-10 10:00";
    private final List<String> PARAMETER_LIST = List.of("Movie","Room",DEFAULT_DATE);
    private final Movie DEFAULT_MOVIE = new Movie("Movie","genre",100);
    private final Room DEFAULT_ROOM = new Room("Room",10,10);

    private CreateScreeningCommand underTest;
    private LoggedInUserTrackImpl userService;

    @Mock
    private ConnectToScreeningRepository screeningRepository;

    @Mock
    private ConnectToRoomRepository roomRepository;

    @Mock
    private ConnectToMovieRepository movieRepository;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        userService = new LoggedInUserTrackImpl(new DefaultUser());
        underTest = new CreateScreeningCommand(userService, screeningRepository, roomRepository, movieRepository, new DateValidationImpl());
        userService.updateCurrentUser(new Administrator(new AdminCredentialsProvider()));
    }

    @Test
    public void testExecuteShouldNotCreateScreeningWhenNotSignedIntoAPrivilegedAccount()
    {
        //Given
        userService.updateCurrentUser(new DefaultUser());
        final String expected = "Unauthorized action!";

        //When
        final String result = underTest.execute(PARAMETER_LIST);

        //Then
        Assertions.assertEquals(expected,result);
    }
    @Test
    public void testExecuteShouldCreateScreeningWhenSignedIntoAPrivilegedAccountAndGivenValidInput()
    {
        //Given
        final String expected = "Alright";
        BDDMockito.given(movieRepository.listMovies()).willReturn(List.of(DEFAULT_MOVIE));
        BDDMockito.given(roomRepository.listRooms()).willReturn(List.of(DEFAULT_ROOM));
        BDDMockito.given(screeningRepository.listScreenings()).willReturn(List.of());

        //When
        final String result = underTest.execute(PARAMETER_LIST);

        //Then
        Assertions.assertEquals(expected,result);
        Mockito.verify(screeningRepository).createScreening(new Screening(DEFAULT_MOVIE,DEFAULT_ROOM,DEFAULT_DATE));
    }

    @Test
    public void testExecuteShouldGiveMessageWhenThereIsNoRoomThatWasGiven()
    {
        //Given
        final String expected = "There is no room like Room";
        final Room room = new Room("RoomName",10,10);
        BDDMockito.given(movieRepository.listMovies()).willReturn(List.of(DEFAULT_MOVIE));
        BDDMockito.given(roomRepository.listRooms()).willReturn(List.of(room));

        //When
        final String result = underTest.execute(PARAMETER_LIST);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldGiveMessageWhenThereIsNoMovieThatWasGiven()
    {
        //Given
        final String expected = "There is no movie like Movie";
        final Movie movie = new Movie("MovieName","genre",100);
        BDDMockito.given(movieRepository.listMovies()).willReturn(List.of(movie));

        //When
        final String result = underTest.execute(PARAMETER_LIST);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldGiveMessageWhenGivenInvalidDate()
    {
        //Given
        final String expected = "That is not a valid date!";
        BDDMockito.given(movieRepository.listMovies()).willReturn(List.of(DEFAULT_MOVIE));
        BDDMockito.given(roomRepository.listRooms()).willReturn(List.of(DEFAULT_ROOM));

        //When
        final String result = underTest.execute(List.of("Movie","Room","Date"));

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldGiveMessageWhenGivenOverlappingDate()
    {
        //Given
        final String expected = "There is an overlapping screening";
        final Screening screening = new Screening(DEFAULT_MOVIE,DEFAULT_ROOM,DEFAULT_DATE);
        BDDMockito.given(movieRepository.listMovies()).willReturn(List.of(DEFAULT_MOVIE));
        BDDMockito.given(roomRepository.listRooms()).willReturn(List.of(DEFAULT_ROOM));
        BDDMockito.given(screeningRepository.listScreenings()).willReturn(List.of(screening));

        //When
        final String result = underTest.execute(List.of("Movie","Room","2021-10-10 10:30"));

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldGiveMessageWhenGivenDateInsideBreakTime()
    {
        //Given
        final String expected = "This would start in the break period after another screening in this room";
        final Screening screening = new Screening(DEFAULT_MOVIE,DEFAULT_ROOM,DEFAULT_DATE);
        BDDMockito.given(movieRepository.listMovies()).willReturn(List.of(DEFAULT_MOVIE));
        BDDMockito.given(roomRepository.listRooms()).willReturn(List.of(DEFAULT_ROOM));
        BDDMockito.given(screeningRepository.listScreenings()).willReturn(List.of(screening));

        //When
        final String result = underTest.execute(List.of("Movie","Room","2021-10-10 11:45"));

        //Then
        Assertions.assertEquals(expected,result);
    }

}
