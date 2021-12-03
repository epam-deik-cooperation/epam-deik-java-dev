package hu.unideb.inf.ticketservice.command.price;

import hu.unideb.inf.ticketservice.command.impl.price.ShowPriceCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.repository.SeatRepository;
import hu.unideb.inf.ticketservice.service.PriceService;
import hu.unideb.inf.ticketservice.service.SeatValidationService;
import hu.unideb.inf.ticketservice.service.connection.impl.BookedSeatRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.ScreeningRepositoryConnection;
import hu.unideb.inf.ticketservice.service.impl.PriceServiceImpl;
import hu.unideb.inf.ticketservice.service.impl.SeatValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

public class TestShowPriceCommand {

    private final List<String> parameters = List.of("Movie","Room","2021-10-10 10:00","5,5 5,6");

    private ShowPriceCommand underTest;
    private PriceService priceService;
    private SeatValidationService seatValidationService;
    @Mock
    private ScreeningRepository screeningRepository;
    @Mock
    private SeatRepository seatRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        priceService = new PriceServiceImpl();
        seatValidationService = new SeatValidator(new BookedSeatRepositoryConnection(seatRepository));
        underTest = new ShowPriceCommand(new ScreeningRepositoryConnection(screeningRepository),
                priceService, seatValidationService);
    }


    @Test
    public void testExecuteShouldNotShowPriceWhenGivenNonExistingScreening() {
        //Given
        BDDMockito.given(screeningRepository.findAll()).willReturn(Collections.emptyList());
        final String expected = "There is no screening with movie Movie in room Room at 2021-10-10 10:00";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldShowPriceWhenGivenValidParameters() {
        //Given
        final Movie movie = new Movie("Movie", "genre", 156);
        final Room room = new Room("Room", 10, 10);
        final Screening screening = new Screening(movie, room, "2021-10-10 10:00");
        BDDMockito.given(screeningRepository.findAll()).willReturn(List.of(screening));
        final String expected = "The price for this booking would be 3000 HUF";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }
}
