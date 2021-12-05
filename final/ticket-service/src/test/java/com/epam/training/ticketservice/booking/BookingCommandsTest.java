package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.account.Account;
import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.exception.ConflictException;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.Seat;
import com.epam.training.ticketservice.screening.Screening;
import javassist.NotFoundException;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingCommandsTest {

    private Account account;
    private String movieTitle;
    private String roomName;
    private String date;
    private String seats;


    @Mock
    BookingService bookingService;

    @Mock
    AccountService accountService;

    @InjectMocks
    BookingCommands bookingCommands;

    @BeforeEach
    void setUp() {

        movieTitle = "test movie";
        roomName = "test room";
        date = "2021-11-11 11:11";
        seats = "1,1";

        account = Account.builder().userName("user")
                .password("pw")
                .bookings(List.of())
                .build();

        Authentication authentication = new TestingAuthenticationToken(
                account.getUserName(),
                account,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }


    @Test
    public void testBookShouldInvokeCreateBooking() throws NotFoundException, ConflictException {

        // Given
        Booking testBooking = Booking.builder()
                .price(2000)
                .seats(List.of(new Seat(1, 1)))
                .build();

        // When
        when(accountService.findByUserName(anyString())).thenReturn(account);
        when(bookingService.mapToBooking(movieTitle, roomName, date, seats, account)).thenReturn(testBooking);
        bookingCommands.book(movieTitle, roomName, date, seats);

        // Then
        verify(bookingService, times(1)).createBooking(testBooking);
    }

    @Test
    public void testBookShouldCatchAndReturnNotFoundExceptionMessageWhenThrownFromService()
            throws ConflictException, NotFoundException {

        // Given
        Booking testBooking = Booking.builder()
                .price(2000)
                .seats(List.of(new Seat(1, 1)))
                .build();

        String expectedMessage = "test";
        NotFoundException e = new NotFoundException(expectedMessage);

        // When
        doThrow(e).when(bookingService).createBooking(any(Booking.class));
        when(accountService.findByUserName(anyString())).thenReturn(account);
        when(bookingService.mapToBooking(movieTitle, roomName, date, seats, account)).thenReturn(testBooking);


        String actualMessage = bookingCommands.book(movieTitle, roomName, date, seats);

        // Then
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testBookShouldCatchAndReturnConflictExceptionMessageWhenThrownFromService()
            throws ConflictException, NotFoundException {

        // Given
        Booking testBooking = Booking.builder()
                .price(2000)
                .seats(List.of(new Seat(1, 1)))
                .build();

        String expectedMessage = "test";
        ConflictException e = new ConflictException(expectedMessage);

        // When
        doThrow(e).when(bookingService).createBooking(any(Booking.class));
        when(accountService.findByUserName(anyString())).thenReturn(account);
        when(bookingService.mapToBooking(movieTitle, roomName, date, seats, account)).thenReturn(testBooking);


        String actualMessage = bookingCommands.book(movieTitle, roomName, date, seats);

        // Then
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testShowPriceForShouldReturnStringFromServiceLayer() throws NotFoundException, ConflictException {

        // Given
        String expectedMessage = "test";

        Room room = Room.builder()
                .numberOfColumns(10)
                .numberOfRows(10)
                .name("test").build();

        Movie movie = Movie.builder()
                .title("test")
                .genre("test")
                .length(100)
                .build();

        Screening screening = Screening.builder()
                .room(room)
                .movie(movie)
                .date(LocalDateTime.of(2021, 11, 11, 11, 11))
                .build();

        Booking booking = Booking.builder()
                .screening(screening)
                .price(1000)
                .seats(List.of(new Seat(1, 1)))
                .build();

        // When
        when(bookingService.mapToBooking(movieTitle, roomName, date, seats)).thenReturn(booking);
        when(bookingService.showPriceForBooking(booking)).thenReturn(expectedMessage);
        String actualMessage = bookingCommands.showPriceFor(movieTitle, roomName, date, seats);

        // Then
        assertEquals(expectedMessage, actualMessage);


    }

    @Test
    public void testShowPriceForShouldReturnExceptionMessageWhenServiceLayerThrowsNotFoundException()
            throws ConflictException, NotFoundException {

        // Given
        String expectedString = "test";
        String actualString = "";

        Room room = Room.builder()
                .numberOfColumns(10)
                .numberOfRows(10)
                .name("test").build();

        Movie movie = Movie.builder()
                .title("test")
                .genre("test")
                .length(100)
                .build();

        Screening screening = Screening.builder()
                .room(room)
                .movie(movie)
                .date(LocalDateTime.of(2021, 11, 11, 11, 11))
                .build();

        Booking booking = Booking.builder()
                .screening(screening)
                .price(1000)
                .seats(List.of(new Seat(1, 1)))
                .build();

        // When
        when(bookingService.mapToBooking(movieTitle, roomName, date, seats)).thenReturn(booking);
        when(bookingService.showPriceForBooking(booking))
                .thenThrow(new NotFoundException(expectedString));

        actualString = bookingCommands.showPriceFor(movieTitle, roomName, date, seats);


        // Then

        assertEquals(expectedString, actualString);


    }

}
