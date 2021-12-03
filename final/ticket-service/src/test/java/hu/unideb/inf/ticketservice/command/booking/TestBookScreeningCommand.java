package hu.unideb.inf.ticketservice.command.booking;

import hu.unideb.inf.ticketservice.command.impl.booking.BookScreeningCommand;
import hu.unideb.inf.ticketservice.model.*;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.model.user.User;
import hu.unideb.inf.ticketservice.repository.BookingRepository;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.repository.SeatRepository;
import hu.unideb.inf.ticketservice.repository.UserRepository;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.PriceService;
import hu.unideb.inf.ticketservice.service.SeatValidationService;
import hu.unideb.inf.ticketservice.service.connection.impl.BookedSeatRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.BookingRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.ScreeningRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.UserRepositoryConnection;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
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

public class TestBookScreeningCommand {

    private final List<String> parameters = List.of("Movie", "Room", "2021-10-10 10:00", "5,6");
    private final Movie movie = new Movie("Movie", "genre", 156);
    private final Room room = new Room("Room", 10, 10);
    private final Screening screening = new Screening(movie, room, "2021-10-10 10:00");
    private final List<Seat> seats = List.of(new Seat(5, 1, room));

    private BookScreeningCommand underTest;
    private SeatValidationService seatValidationService;
    private LoggedInUserTrackService loggedInUserTrack;
    private PriceService priceService;

    @Mock
    private SeatRepository bookedSeatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ScreeningRepository screeningRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        priceService = new PriceServiceImpl();
        seatValidationService = new SeatValidator(new BookedSeatRepositoryConnection(bookedSeatRepository));
        User user = new User("Test", "Test", false);
        loggedInUserTrack = new LoggedInUserTrackImpl(user);
        underTest = new BookScreeningCommand(new BookedSeatRepositoryConnection(bookedSeatRepository),
                new BookingRepositoryConnection(bookingRepository), seatValidationService,
                new ScreeningRepositoryConnection(screeningRepository), loggedInUserTrack,
                new UserRepositoryConnection(userRepository), priceService);
    }

    @Test
    public void testExecuteShouldNotBookAScreeningWhenNotSignedIn() {
        //Given
        loggedInUserTrack.updateCurrentUser(new DefaultUser());
        final String expected = "Sign in to book a seat";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);

    }

    @Test
    public void testExecuteShouldNotBookAScreeningWhenThereIsNoScreeningLikeThatWasGiven() {
        //Given
        BDDMockito.given(screeningRepository.findAll()).willReturn(Collections.emptyList());
        final String expected = "There is no screening with movie Movie in room Room at 2021-10-10 10:00";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldNotBookAScreeningWhenTheGivenSeatIsAlreadyBooked() {
        //Given
        final List<Seat> seatsBooked = List.of(new Seat(5, 6, room));
        BDDMockito.given(screeningRepository.findAll()).willReturn(List.of(screening));
        BDDMockito.given(bookedSeatRepository.findAll()).willReturn(seatsBooked);
        final String expected = "Seat (5,6) is already taken";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldNotBookAScreeningWhenTheGivenSeatIsInvalid() {
        //Given
        final Room roomSmall = new Room("Room", 5, 5);
        final Screening screening = new Screening(movie, roomSmall, "2021-10-10 10:00");
        final List<Seat> seats = List.of(new Seat(5, 1, roomSmall));
        BDDMockito.given(screeningRepository.findAll()).willReturn(List.of(screening));
        BDDMockito.given(bookedSeatRepository.findAll()).willReturn(seats);
        final String expected = "Seat (5,6) does not exist in this room";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldBookAScreeningWhenTheGivenSeatAndScreeningIsValid() {
        //Given
        BDDMockito.given(screeningRepository.findAll()).willReturn(List.of(screening));
        BDDMockito.given(bookedSeatRepository.findAll()).willReturn(seats);
        final String expected = "Seats booked: (5,6); the price for this booking is 1500 HUF";

        //When
        final String result = underTest.execute(parameters);

        //Then
        Assertions.assertEquals(expected,result);
    }

}
