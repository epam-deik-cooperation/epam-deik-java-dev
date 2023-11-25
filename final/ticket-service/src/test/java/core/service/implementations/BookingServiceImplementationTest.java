package core.service.implementations;

import com.epam.training.ticketservice.core.dto.BookingDto;
import com.epam.training.ticketservice.core.dto.UserDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.model.Booking;
import com.epam.training.ticketservice.core.model.Movie;
import com.epam.training.ticketservice.core.model.Room;
import com.epam.training.ticketservice.core.model.Screening;
import com.epam.training.ticketservice.core.model.Seat;
import com.epam.training.ticketservice.core.model.User;
import com.epam.training.ticketservice.core.repository.BookingRepository;
import com.epam.training.ticketservice.core.repository.MovieRepository;
import com.epam.training.ticketservice.core.repository.RoomRepository;
import com.epam.training.ticketservice.core.repository.ScreeningRepository;
import com.epam.training.ticketservice.core.repository.UserRepository;
import com.epam.training.ticketservice.core.service.implementations.BookingServiceImplementation;
import com.epam.training.ticketservice.core.service.implementations.UserServiceImplementation;
import com.epam.training.ticketservice.core.service.interfaces.UserServiceInterface;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class BookingServiceImplementationTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);
    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final UserServiceInterface userServiceInterface = spy(new UserServiceImplementation(userRepository));
    private final BookingServiceImplementation underTest = new BookingServiceImplementation(
            movieRepository, roomRepository, screeningRepository, bookingRepository,
            userRepository, userServiceInterface);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Movie movie = new Movie("Terminator", "Action", 108);
    private final Room room = new Room("Pedersoli", 10, 10);
    private final Screening screening = new Screening(movie,
            room, LocalDateTime.parse("2023-11-20 10:00", formatter));

    @Test
    void testBookShouldBookSeatsAndCalculatePriceWhenEverythingIsOk() throws DoesNotExists, AlreadyExists {
        // Given
        String userName = "testUser";
        User testUserDto = new User(userName, "testPassword", User.Role.USER);
        String seats = "1,1";
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(
                movie, room, screening.getScreeningDate())).thenReturn(Optional.of(screening));
        doReturn(Optional.of(new UserDto(userName, User.Role.USER))).when(userServiceInterface).describeAccount();
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(testUserDto));

        // When
        String result = underTest.book(movie.getMovieName(), room.getRoomName(), screening.getScreeningDate(), seats);

        // Then
        String expected = "Seats booked: (1,1); the price for this booking is 1500 HUF";
        assertEquals(expected, result);
        verify(movieRepository).findByMovieName(movie.getMovieName());
        verify(roomRepository).findByRoomName(room.getRoomName());
        verify(screeningRepository).findScreeningByMovieAndRoomAndScreeningDate(movie, room,
                screening.getScreeningDate());
        verify(userServiceInterface).describeAccount();
        verify(userRepository).findByUserName(userName);
        verify(bookingRepository).findBookingsByScreening(screening);
        verify(bookingRepository).save(any(Booking.class));
        verifyNoMoreInteractions(movieRepository, roomRepository, screeningRepository, userServiceInterface,
                userRepository, bookingRepository);
    }

    @Test
    void testBookShouldThrowDoesNotExistsWhenTheMovieOrRoomDoesNotExist() {
        // Given
        String seats = "1,1";
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.empty());
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.empty());

        // When
        assertThrows(DoesNotExists.class, () -> underTest.book(movie.getMovieName(), room.getRoomName(),
                screening.getScreeningDate(), seats));

        // Then
        verify(movieRepository).findByMovieName(movie.getMovieName());
        verify(roomRepository).findByRoomName(room.getRoomName());
        verifyNoMoreInteractions(movieRepository, roomRepository, screeningRepository, userServiceInterface,
                userRepository, bookingRepository);
    }

    @Test
    void testBookShouldThrowDoesNotExistsWhenTheMovieDoesNotExist() {
        // Given
        String seats = "1,1";
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.empty());

        // When
        assertThrows(DoesNotExists.class, () -> underTest.book(movie.getMovieName(), room.getRoomName(),
                screening.getScreeningDate(), seats));

        // Then
        verify(movieRepository).findByMovieName(movie.getMovieName());
        verify(roomRepository).findByRoomName(room.getRoomName());
        verifyNoMoreInteractions(movieRepository, roomRepository, screeningRepository, userServiceInterface,
                userRepository, bookingRepository);
    }

    @Test
    void testBookShouldThrowDoesNotExistsWhenTheRoomDoesNotExist() {
        // Given
        String seats = "1,1";
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.empty());
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));

        // When
        assertThrows(DoesNotExists.class, () -> underTest.book(movie.getMovieName(),
                room.getRoomName(), screening.getScreeningDate(), seats));

        // Then
        verify(movieRepository).findByMovieName(movie.getMovieName());
        verify(roomRepository).findByRoomName(room.getRoomName());
        verifyNoMoreInteractions(movieRepository, roomRepository, screeningRepository, userServiceInterface,
                userRepository, bookingRepository);
    }

    @Test
    void testBookShouldThrowDoesNotExistsWhenTheScreeningDoesNotExist() {
        // Given
        String seats = "1,1";
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(
                movie, room, screening.getScreeningDate())).thenReturn(Optional.empty());

        // When
        assertThrows(DoesNotExists.class, () -> underTest.book(movie.getMovieName(),
                room.getRoomName(), screening.getScreeningDate(), seats));

        // Then
        verify(movieRepository).findByMovieName(movie.getMovieName());
        verify(roomRepository).findByRoomName(room.getRoomName());
        verify(screeningRepository).findScreeningByMovieAndRoomAndScreeningDate(movie,
                room, screening.getScreeningDate());
        verifyNoMoreInteractions(movieRepository, roomRepository, screeningRepository, userServiceInterface,
                userRepository, bookingRepository);
    }

    @Test
    void testBookShouldThrowDoesNotExistsWhenTheChairDoesNotExistInRoomUpper() {
        // Given
        String userName = "testUser";
        User testUserDto = new User(userName, "testPassword", User.Role.USER);
        String seats = "11,1";
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(
                movie, room, screening.getScreeningDate())).thenReturn(Optional.of(screening));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(
                movie, room, screening.getScreeningDate())).thenReturn(Optional.of(screening));
        doReturn(Optional.of(new UserDto(userName, User.Role.USER))).when(userServiceInterface).describeAccount();
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(testUserDto));

        // When
        assertThrows(DoesNotExists.class, () -> underTest.book(movie.getMovieName(),
                room.getRoomName(), screening.getScreeningDate(), seats));

        // Then
        verify(movieRepository).findByMovieName(movie.getMovieName());
        verify(roomRepository).findByRoomName(room.getRoomName());
        verify(screeningRepository).findScreeningByMovieAndRoomAndScreeningDate(movie,
                room, screening.getScreeningDate());
    }

    @Test
    void testBookShouldThrowDoesNotExistsWhenTheChairDoesNotExistInRoomLower() {
        // Given
        String userName = "testUser";
        User testUserDto = new User(userName, "testPassword", User.Role.USER);
        String seats = "1,0";
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(
                movie, room, screening.getScreeningDate())).thenReturn(Optional.of(screening));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(
                movie, room, screening.getScreeningDate())).thenReturn(Optional.of(screening));
        doReturn(Optional.of(new UserDto(userName, User.Role.USER))).when(userServiceInterface).describeAccount();
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(testUserDto));

        // When
        assertThrows(DoesNotExists.class, () -> underTest.book(movie.getMovieName(),
                room.getRoomName(), screening.getScreeningDate(), seats));

        // Then
        verify(movieRepository).findByMovieName(movie.getMovieName());
        verify(roomRepository).findByRoomName(room.getRoomName());
        verify(screeningRepository).findScreeningByMovieAndRoomAndScreeningDate(movie,
                room, screening.getScreeningDate());
    }

    @Test
    void testBookShouldThrowAlreadyExistsWhenSeatIsAlreadyTaken() throws DoesNotExists, AlreadyExists {
        // Given
        String userName = "testUser";
        User testUserDto = new User(userName, "testPassword", User.Role.USER);
        String seats = "1,1";
        Seat seat = new Seat(1, 1);
        Booking booking = new Booking(screening, testUserDto, Collections.singletonList(seat), 1500);
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(
                movie, room, screening.getScreeningDate())).thenReturn(Optional.of(screening));
        doReturn(Optional.of(new UserDto(userName, User.Role.USER))).when(userServiceInterface).describeAccount();
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(testUserDto));
        when(bookingRepository.findBookingsByScreening(screening)).thenReturn(List.of(booking));

        // When
        assertThrows(AlreadyExists.class, () -> underTest.book(movie.getMovieName(),
                room.getRoomName(), screening.getScreeningDate(), seats));

        // Then
        verify(movieRepository).findByMovieName(movie.getMovieName());
        verify(roomRepository).findByRoomName(room.getRoomName());
        verify(screeningRepository).findScreeningByMovieAndRoomAndScreeningDate(movie, room,
                screening.getScreeningDate());
        verify(userServiceInterface).describeAccount();
        verify(userRepository).findByUserName(userName);
        verify(bookingRepository).findBookingsByScreening(screening);
        verifyNoMoreInteractions(movieRepository, roomRepository, screeningRepository, userServiceInterface,
                userRepository, bookingRepository);
    }

    @Test
    void testListBookingsShouldReturnBookingDtoListWhenABookingIsSaved() {
        // Given
        String userName = "testUser";
        User testUserDto = new User(userName, "testPassword", User.Role.USER);
        Seat seat = new Seat(1, 1);
        Booking booking = new Booking(screening, testUserDto, Collections.singletonList(seat), 1500);
        when(bookingRepository.findAll()).thenReturn(Collections.singletonList(booking));

        // When
        List<BookingDto> actual = underTest.listBookings();

        // Then
        verify(bookingRepository).findAll();
        assertEquals(1, actual.size());
        assertEquals(booking.getScreening().getMovie().getMovieName(),
                actual.get(0).getScreeningDto().getMovieDto().getMovieName());
        assertEquals(booking.getScreening().getRoom().getRoomName(),
                actual.get(0).getScreeningDto().getRoomDto().getRoomName());
        assertEquals(booking.getScreening().getScreeningDate(), actual.get(0).getScreeningDto().getScreeningDate());
        assertEquals(booking.getSeatList(), actual.get(0).getSeatList());
        assertEquals(booking.getUser().getUserName(), actual.get(0).getUserDto().userName());
        assertEquals(booking.getPrice(), actual.get(0).getPrice());
    }

    @Test
    void testListBookingsShouldReturnOptionalEmptyWhenABookingIsSaved() {
        // Given

        // When
        List<BookingDto> actual = underTest.listBookings();

        // Then
        assertEquals(emptyList(), actual);
    }
}