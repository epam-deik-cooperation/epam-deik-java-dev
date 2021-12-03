package hu.unideb.inf.ticketservice.command.price;

import hu.unideb.inf.ticketservice.command.impl.price.AttachPriceComponentToMovieCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.repository.ComponentRepository;
import hu.unideb.inf.ticketservice.repository.MovieRepository;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.impl.ComponentRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.MovieRepositoryConnection;
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

public class TestAttachComponentToMovieCommand {

    private final List<String> parameters = List.of("Component","Movie");

    private LoggedInUserTrackService userTrackService;
    private AttachPriceComponentToMovieCommand underTest;

    @Mock
    private ComponentRepository componentRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private ScreeningRepository screeningRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userTrackService = new LoggedInUserTrackImpl(new Administrator(new AdminCredentialsProvider()));
        underTest = new AttachPriceComponentToMovieCommand(userTrackService,
                new ComponentRepositoryConnection(componentRepository),
                new MovieRepositoryConnection(movieRepository,screeningRepository));
    }

    @Test
    public void testExecuteShouldNotAttachPriceToMovieWhenNotSignedIntoAPrivilegedAccount() {
        //Given
        userTrackService.updateCurrentUser(new DefaultUser());
        final String expected = "Unauthorized action!";

        //When
        final String result = underTest.execute(Collections.emptyList());

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldNotAttachPriceToMovieWhenGivenNonExistingComponent() {
        //Given
        BDDMockito.given(componentRepository.findAll()).willReturn(Collections.emptyList());
        final String expected = "There is no component with name Component";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldNotAttachPriceToMovieWhenGivenNonExistingMovie() {
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
    public void testExecuteShouldAttachPriceToMovieWhenGivenValidParameters() {
        //Given
        final PriceComponent priceComponent = new PriceComponent("Component",1500);
        final List<PriceComponent> components = List.of(priceComponent);
        BDDMockito.given(componentRepository.findAll()).willReturn(components);
        final List<Movie> movies = List.of(new Movie("Movie","genre",156));
        BDDMockito.given(movieRepository.findAll()).willReturn(movies);
        final String expected = "Alright";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
        Mockito.verify(movieRepository).updateComponent("Movie",priceComponent);
    }
}
