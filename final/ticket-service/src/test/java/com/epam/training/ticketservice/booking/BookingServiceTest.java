package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.exception.ConflictException;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.Seat;
import com.epam.training.ticketservice.screening.Screening;
import javassist.NotFoundException;
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
    public void createBookingShouldThrowNotFoundExceptionIfSeatDoesNotExistInRoom() {

        // Given
        testBooking.setSeats(List.of(new Seat(11, 10)));

        // When


        // Then
        assertThrows(NotFoundException.class, () -> bookingService.createBooking(testBooking));
        verify(bookingRepository, times(0)).save(testBooking);

    }


    @Test
    public void createBookingShouldThrowConflictExceptionIfSeatIsAlreadyBooked() {

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


}
