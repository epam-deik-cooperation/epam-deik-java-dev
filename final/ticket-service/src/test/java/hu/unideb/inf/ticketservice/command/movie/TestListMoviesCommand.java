package hu.unideb.inf.ticketservice.command.movie;

import hu.unideb.inf.ticketservice.command.impl.movie.ListMoviesCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class TestListMoviesCommand {

    private ListMoviesCommand underTest;
    @Mock
    private ConnectToRepositoriesService repositoriesService;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        underTest = new ListMoviesCommand(repositoriesService);
    }

    @Test
    public void testExecuteShouldReturnAListOfMoviesWhenMovieDatabaseIsNotEmpty()
    {
        //Given
        final Movie movie = new Movie("Name","genre",156);
        BDDMockito.given(repositoriesService.listMovies()).willReturn(List.of(movie));
        final String expected = movie.toString();

        //When
        final String result = underTest.execute(null);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldReturnAMessageWhenMovieDatabaseIsEmpty()
    {
        //Given
        final String expected = "There are no movies at the moment";

        //When
        final String result = underTest.execute(null);

        //Then
        Assertions.assertEquals(expected,result);
    }

}
