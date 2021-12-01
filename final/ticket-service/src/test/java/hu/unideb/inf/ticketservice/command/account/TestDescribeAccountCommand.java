package hu.unideb.inf.ticketservice.command.account;

import hu.unideb.inf.ticketservice.command.impl.account.DescribeAccountCommand;
import hu.unideb.inf.ticketservice.model.*;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.model.user.User;
import hu.unideb.inf.ticketservice.model.user.UserInterface;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestDescribeAccountCommand {

    private DescribeAccountCommand underTest;
    private LoggedInUserTrackService loggedInUserTrackService;
    private AdminCredentialsProvider credentialsProvider;

    @BeforeEach
    public void setup() {
        credentialsProvider = new AdminCredentialsProvider();
        loggedInUserTrackService = new LoggedInUserTrackImpl(new DefaultUser());
        underTest = new DescribeAccountCommand(loggedInUserTrackService);
    }

    @Test
    public void testExecuteShouldReturnCorrectResultWhenGivenPrivilegedAccount() {
        //Given
        String expected = "Signed in with privileged account 'admin'";
        loggedInUserTrackService.updateCurrentUser(new Administrator(credentialsProvider));

        //When
        String result = underTest.execute(null);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldReturnCorrectStringWhenGivenDefaultAccount() {
        //Given
        String expected = "You are not signed in";

        //When
        String result = underTest.execute(null);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldReturnCorrectStringWhenGivenANonDefaultAccountWithoutBookings() {
        //Given
        final UserInterface user = new User("test","test",false);
        loggedInUserTrackService.updateCurrentUser(user);
        final String expected = """
                Signed in with account 'test'
                You have not booked any tickets yet""";

        //When
        final String result = underTest.execute(null);

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldReturnCorrectStringWhenGivenANonDefaultAccountWithBookings() {
        //Given
        final UserInterface user = new User("test","test",false);
        final Movie movie = new Movie("Title", "genre", 156);
        final Room room = new Room("Name", 10, 10);
        final Screening screening = new Screening(movie, room, "2021-10-10 14:00");
        final List<Seat> seats = List.of(new Seat(5, 6, room), new Seat(5, 7, room));
        final Booking booking = new Booking(screening, 3000, seats);
        loggedInUserTrackService.updateCurrentUser(user);
        ((User)loggedInUserTrackService.getCurrentUser()).addBooking(booking);
        final String expected = """
                Signed in with account 'test'
                Your previous bookings are
                Seats (5,6), (5,7) on Title in room Name starting at 2021-10-10 14:00 for 3000 HUF""";

        //When
        final String result = underTest.execute(null);

        //Then
        Assertions.assertEquals(expected,result);
    }

}
