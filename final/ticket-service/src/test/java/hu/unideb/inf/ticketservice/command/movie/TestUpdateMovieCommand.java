package hu.unideb.inf.ticketservice.command.movie;

import hu.unideb.inf.ticketservice.command.impl.movie.UpdateMovieCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.user.AbstractUser;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.service.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class TestUpdateMovieCommand {

    private final AdminCredentialsProvider credentialsProvider = new AdminCredentialsProvider();
    private final AbstractUser ADMINISTRATOR = new Administrator(credentialsProvider);
    private final List<String> PARAMETER_LIST = List.of("Movie","genre","156");

    private UpdateMovieCommand underTest;
    private LoggedInUserTrackService userService;

    @Mock
    private ConnectToRepositoriesService repositoriesService;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        userService = new LoggedInUserTrackImpl(new DefaultUser());
        underTest = new UpdateMovieCommand(userService,repositoriesService);
    }

    @Test
    public void testExecuteShouldUpdateAMovieWhenThereIsAMatchingMovie()
    {
        //Given
        final String expected = "Alright";
        final Movie movie = new Movie("Movie","genre",156);
        final Movie movieToBeCalledWith = new Movie("Movie","newGenre",156);
        BDDMockito.given(repositoriesService.listMovies()).willReturn(List.of(movie));
        userService.updateCurrentUser(ADMINISTRATOR);

        //When
        final String result = underTest.execute(List.of("Movie","newGenre","156"));

        //Then
        Assertions.assertEquals(expected,result);
        Mockito.verify(repositoriesService).updateMovie("Movie",movieToBeCalledWith);
    }

    @Test
    public void testExecuteShouldGiveAMessageWhenThereIsNotAMatchingMovie()
    {
        //Given
        final String expected = "No such movie like Movie";
        BDDMockito.given(repositoriesService.listMovies()).willReturn(List.of());
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
