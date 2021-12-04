package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.account.Account;
import com.epam.training.ticketservice.booking.price.PriceCalculator;
import com.epam.training.ticketservice.exception.ConflictException;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.Seat;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    BookingRepository bookingRepository;

    @Mock
    ScreeningService screeningService;

    @Mock
    PriceCalculator priceCalculator;

    @InjectMocks
    BookingService bookingService;

    private Room testRoom;
    private Movie testMovie;
    private Screening testScreening;
    private Booking testBooking;

    @BeforeEach
    void setUp() {

        testRoom = Room.builder()
                .numberOfColumns(10)
                .numberOfRows(10)
                .name("testRoom")
                .build();

        testMovie = Movie.builder()
                .title("testMovie")
                .genre("test")
                .length(100)
                .build();

        testScreening = Screening.builder()
                .movie(testMovie)
                .room(testRoom)
                .date(LocalDateTime.of(2020, Month.APRIL, 10, 10, 10))
                .build();

        testBooking = Booking.builder()
                .screening(testScreening)
                .build();

    }

    @Test
    public void testCreateBookingShouldThrowNotFoundExceptionIfSeatDoesNotExistInRoom() {

        // Given
        testBooking.setSeats(List.of(new Seat(11, 10)));

        // When


        // Then
        assertThrows(NotFoundException.class, () -> bookingService.createBooking(testBooking));
        verify(bookingRepository, times(0)).save(testBooking);

    }


    @Test
    public void testCreateBookingShouldThrowConflictExceptionIfSeatIsAlreadyBooked() {

        // Given
        List<Booking> testBookingList = new ArrayList<>();

        testBooking.setSeats(List.of(new Seat(1, 5)));
        testBookingList.add(testBooking);

        Booking testNewBooking = Booking.builder()
                .screening(testScreening)
                .seats(List.of(new Seat(1, 5)))
                .build();

        // When
        when(bookingRepository.findAllByScreening_MovieAndScreening_RoomAndScreening_Date(
                any(Movie.class), any(Room.class), any(LocalDateTime.class))).thenReturn(testBookingList);


        // Then
        assertThrows(ConflictException.class, () -> bookingService.createBooking(testNewBooking));
        verify(bookingRepository, times(0)).save(testNewBooking);
    }

    @Test
    public void testCreateBooking() throws NotFoundException, ConflictException {

        // Given
        List<Booking> testBookingList = new ArrayList<>();

        testBooking.setSeats(List.of(new Seat(1, 5)));
        testBookingList.add(testBooking);

        Booking testNewBooking = Booking.builder()
                .screening(testScreening)
                .seats(List.of(new Seat(2, 5)))
                .build();


        // When
        when(bookingRepository.findAllByScreening_MovieAndScreening_RoomAndScreening_Date(
                any(Movie.class), any(Room.class), any(LocalDateTime.class))).thenReturn(testBookingList);

        bookingService.createBooking(testNewBooking);


        // Then
        verify(bookingRepository, times(1)).save(testNewBooking);
    }

    @Test
    public void testShowPriceForBookingShouldReturnStringWithPriceOfTheBooking()
            throws NotFoundException, ConflictException {

        // Given
        testBooking.setPrice(1500);
        testBooking.setSeats(List.of(new Seat(2, 3)));

        String expectedString = String.format("The price for this booking would be %d HUF", testBooking.getPrice());

        StringBuilder sb = new StringBuilder();

        testBooking.getSeats().forEach(x ->
                sb.append(x.getRowIndex())
                        .append(",")
                        .append(x.getColumnIndex())
                        .append(" "));


        // When
        String actualString = bookingService.showPriceForBooking(testBooking);


        // Then
        Assertions.assertEquals(expectedString, actualString);


    }

    @Test
    public void testShowPriceForBookingShouldReturnNoBookingFoundMessageIfScreeningIsNull() {

        // Given
        testBooking.setSeats(List.of(new Seat(2, 3)));

        String expectedString = "No possible booking found with such properties";

        StringBuilder sb = new StringBuilder();
        String actualString = "";

        testBooking.getSeats().forEach(x ->
                sb.append(x.getRowIndex())
                        .append(",")
                        .append(x.getColumnIndex())
                        .append(" "));

        // When
        testBooking.setScreening(null);

        try {
            bookingService.showPriceForBooking(testBooking);
        } catch (NotFoundException | ConflictException e) {
            actualString = e.getMessage();
        }


        // Then
        Assertions.assertEquals(expectedString, actualString);


    }

    @Test
    public void testMapToBookingShouldReturnBookingWithFourProperties() throws NotFoundException {

        // Given
        String seats = "2,2 1,3";
        testBooking.setSeats(List.of(new Seat(2, 2), new Seat(1, 3)));

        // When
        when(screeningService.getScreeningByProperties(testMovie.getTitle(),
                testRoom.getName(),
                testScreening.getDate().toString())).thenReturn(testScreening);

        Booking booking = bookingService.mapToBooking(testMovie.getTitle(),
                testRoom.getName(),
                testScreening.getDate().toString(),
                seats);


        // Then
        Assertions.assertEquals(testBooking, booking);

    }

    @Test
    public void testMapToBookingShouldReturnBookingWithFiveProperties() throws NotFoundException {

        // Given
        String seats = "2,2 1,3";
        Account account = new Account();
        testBooking.setSeats(List.of(new Seat(2, 2), new Seat(1, 3)));
        testBooking.setAccount(account);

        // When
        when(screeningService.getScreeningByProperties(testMovie.getTitle(),
                testRoom.getName(),
                testScreening.getDate().toString())).thenReturn(testScreening);

        Booking booking = bookingService.mapToBooking(testMovie.getTitle(),
                testRoom.getName(),
                testScreening.getDate().toString(),
                seats,
                account);



        // Then
        Assertions.assertEquals(testBooking, booking);

    }


}
