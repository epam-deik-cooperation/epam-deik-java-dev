package hu.unideb.inf.ticketservice.command.screening;

import hu.unideb.inf.ticketservice.command.impl.screening.ListScreeningCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class TestListScreeningCommand {

    private ListScreeningCommand underTest;
    @Mock
    private ConnectToRepositoriesService repositoriesService;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        underTest = new ListScreeningCommand(repositoriesService);
    }

    @Test
    public void testExecuteShouldReturnAListOfScreeningsWhenScreeningDatabaseIsNotEmpty()
    {
        //Given
        final Room room = new Room("Room",10,10);
        final Movie movie = new Movie("Movie","genre",156);
        final Screening screening = new Screening(movie,room,"2021-10-10 10:00");
        BDDMockito.given(repositoriesService.listScreenings()).willReturn(List.of(screening));
        final String expected = screening.toString();

        //When
        final String result = underTest.execute(null);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldReturnAMessageWhenScreeningDatabaseIsEmpty()
    {
        //Given
        final String expected = "There are no screenings";

        //When
        final String result = underTest.execute(null);

        //Then
        Assertions.assertEquals(expected,result);
    }

}
