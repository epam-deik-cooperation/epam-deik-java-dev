package hu.unideb.inf.ticketservice.command.movie;

import hu.unideb.inf.ticketservice.command.impl.movie.CreateMovieCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.connection.ConnectToMovieRepository;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class TestCreateMovieCommand {

    private final List<String> PARAMETER_LIST = List.of("Movie","genre","156");

    private CreateMovieCommand underTest;
    private LoggedInUserTrackImpl userService;
    private AdminCredentialsProvider credentialsProvider;
    @Mock
    private ConnectToMovieRepository movieRepository;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        credentialsProvider = new AdminCredentialsProvider();
        userService = new LoggedInUserTrackImpl(new DefaultUser());
        underTest = new CreateMovieCommand(userService, movieRepository);
    }

    @Test
    public void testExecuteShouldNotCreateMovieWhenNotSignedIntoAPrivilegedAccount()
    {
        //Given
        final String expected = "Unauthorized action!";

        //When
        final String result = underTest.execute(PARAMETER_LIST);

        //Then
        Assertions.assertEquals(expected,result);
    }
    @Test
    public void testExecuteShouldCreateMovieWhenSignedIntoAPrivilegedAccountAndGivenValidInput()
    {
        //Given
        final String expected = "Alright";
        userService.updateCurrentUser(new Administrator(credentialsProvider));

        //When
        final String result = underTest.execute(PARAMETER_LIST);

        //Then
        Assertions.assertEquals(expected,result);
        Mockito.verify(movieRepository).createMovie(new Movie("Movie","genre",156));
    }

}
