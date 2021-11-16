package com.epam.training.ticketservice.booking.price;

import com.epam.training.ticketservice.exception.AlreadyExistsException;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PriceComponentServiceTest {

    @Mock
    PriceComponentRepository pcRepository;

    @Mock
    MovieService movieService;

    @Mock
    RoomService roomService;

    @Mock
    ScreeningService screeningService;

    @InjectMocks
    PriceComponentService pcService;

    Room testRoom;
    Movie testMovie;
    Screening testScreening;
    PriceComponent testComponent;

    @BeforeEach
    void setUp() {

        testRoom = Room.builder()
                .name("testRoom")
                .numberOfRows(1)
                .numberOfColumns(1)
                .build();

        testMovie = Movie.builder()
                .title("testTitle")
                .genre("testGenre")
                .length(10)
                .build();

        testScreening = Screening.builder()
                .room(testRoom)
                .movie(testMovie)
                .date(LocalDateTime.now())
                .build();


        testComponent = PriceComponent.builder()
                .price(1000)
                .name("testComponent")
                .build();

    }

    private static Stream<Arguments> provideArgumentsForTestGetPrice() {
        Room room = Room.builder()
                .name("test")
                .numberOfRows(1)
                .numberOfColumns(1)
                .build();

        Movie movie = Movie.builder()
                .title("test")
                .genre("test")
                .length(10)
                .build();

        Screening screening = Screening.builder()
                .room(room)
                .movie(movie)
                .date(LocalDateTime.now())
                .build();

        return Stream.of(
                Arguments.of(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), 0),
                Arguments.of(Collections.emptyList(), Collections.emptyList(), List.of(screening), 1000),
                Arguments.of(List.of(movie), Collections.emptyList(), List.of(screening), 2000),
                Arguments.of(Collections.emptyList(), List.of(room), List.of(screening), 2000),
                Arguments.of(List.of(movie), List.of(room), List.of(screening), 3000)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForTestGetPrice")
    public void testGetAdditionalPriceShouldReturnAdditionalPriceOfScreening(List<Movie> movies,
                                                                             List<Room> rooms,
                                                                             List<Screening> screenings,
                                                                             int expectedPrice) {

        // Given
        testComponent.setScreenings(screenings);
        testComponent.setMovies(movies);
        testComponent.setRooms(rooms);

        // When
        when(pcRepository.findAll()).thenReturn(List.of(testComponent));
        int actualPrice = pcService.getAdditionalPrice(testScreening);
        if (!screenings.isEmpty()) {
            actualPrice = pcService.getAdditionalPrice(screenings.get(0));
        }

        // Then
        Assertions.assertEquals(expectedPrice, actualPrice);

    }

    @Test
    public void testCreatePriceComponent() throws AlreadyExistsException {

        // Given


        // When
        when(pcRepository.existsByNameContainingIgnoreCase(testComponent.getName())).thenReturn(false);
        pcService.createPriceComponent(testComponent.getName(), testComponent.getPrice());

        // Then
        verify(pcRepository, times(1)).save(testComponent);

    }

    @Test
    public void testCreatePriceComponentShouldThrowExceptionIfComponentAlreadyExists() {

        // Given


        // When
        when(pcRepository.existsByNameContainingIgnoreCase(testComponent.getName())).thenReturn(true);


        // Then
        Assertions.assertThrows(AlreadyExistsException.class,
                () -> pcService.createPriceComponent(testComponent.getName(), testComponent.getPrice()));
        verify(pcRepository, times(0)).save(testComponent);

    }

    @Test
    public void testAttachPriceComponentToMovie() throws NotFoundException {

        // Given


        // When
        when(pcRepository.getByNameContainingIgnoreCase(testComponent.getName()))
                .thenReturn(testComponent);

        when(movieService.findByTitle(testMovie.getTitle()))
                .thenReturn(testMovie);

        pcService.attachPriceComponentToMovie(testComponent.getName(), testMovie.getTitle());


        // Then
        Assertions.assertTrue(testComponent.getMovies().contains(testMovie));

        verify(pcRepository, times(1)).save(testComponent);

    }

    @Test
    public void testAttachPriceComponentToRoom() throws NotFoundException {

        // Given


        // When
        when(pcRepository.getByNameContainingIgnoreCase(testComponent.getName()))
                .thenReturn(testComponent);

        when(roomService.findByName(testRoom.getName()))
                .thenReturn(testRoom);

        pcService.attachPriceComponentToRoom(testComponent.getName(), testRoom.getName());


        // Then
        Assertions.assertTrue(testComponent.getRooms().contains(testRoom));

        verify(pcRepository, times(1)).save(testComponent);

    }

    @Test
    public void testAttachPriceComponentToScreening() throws NotFoundException {

        // Given


        // When
        when(pcRepository.getByNameContainingIgnoreCase(testComponent.getName()))
                .thenReturn(testComponent);

        when(screeningService.getScreeningByProperties(testMovie.getTitle(),
                testRoom.getName(),
                testScreening.getDate().toString()))
                .thenReturn(testScreening);

        pcService.attachPriceComponentToScreening(testComponent.getName(),
                testMovie.getTitle(),
                testRoom.getName(),
                testScreening.getDate().toString());


        // Then
        Assertions.assertTrue(testComponent.getScreenings().contains(testScreening));

        verify(pcRepository, times(1)).save(testComponent);

    }

    @Test
    public void testAttachPriceComponentToMovieShouldThrowNotFoundExceptionIfPriceComponentDoesNotExist() {

        // Given


        // When
        when(pcRepository.getByNameContainingIgnoreCase(testComponent.getName()))
                .thenReturn(null);


        // Then
        Assertions.assertThrows(NotFoundException.class,
                () -> pcService.attachPriceComponentToMovie(testComponent.getName(), testMovie.getTitle()));

        verify(pcRepository, times(0)).save(any(PriceComponent.class));

    }


    @Test
    public void testAttachPriceComponentToRoomShouldThrowNotFoundExceptionIfPriceComponentDoesNotExist() {

        // Given


        // When
        when(pcRepository.getByNameContainingIgnoreCase(testComponent.getName()))
                .thenReturn(null);


        // Then
        Assertions.assertThrows(NotFoundException.class,
                () -> pcService.attachPriceComponentToRoom(testComponent.getName(), testRoom.getName()));

        verify(pcRepository, times(0)).save(any(PriceComponent.class));

    }

    @Test
    public void testAttachPriceComponentToScreeningShouldThrowNotFoundExceptionIfPriceComponentDoesNotExist() {

        // Given


        // When
        when(pcRepository.getByNameContainingIgnoreCase(testComponent.getName()))
                .thenReturn(null);


        // Then
        Assertions.assertThrows(NotFoundException.class,
                () -> pcService.attachPriceComponentToScreening(testComponent.getName(),
                        testMovie.getTitle(),
                        testRoom.getName(),
                        testScreening.getDate().toString()));

        verify(pcRepository, times(0)).save(any(PriceComponent.class));

    }

}
