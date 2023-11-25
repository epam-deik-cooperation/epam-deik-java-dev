package core.service.implementations;

import com.epam.training.ticketservice.core.dto.ScreeningDto;
import com.epam.training.ticketservice.core.exceptions.BreakPeriod;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.exceptions.Overlap;
import com.epam.training.ticketservice.core.model.Movie;
import com.epam.training.ticketservice.core.model.Room;
import com.epam.training.ticketservice.core.model.Screening;
import com.epam.training.ticketservice.core.repository.MovieRepository;
import com.epam.training.ticketservice.core.repository.RoomRepository;
import com.epam.training.ticketservice.core.repository.ScreeningRepository;
import com.epam.training.ticketservice.core.service.implementations.ScreeningServiceImplementation;
import com.epam.training.ticketservice.core.service.interfaces.ScreeningServiceInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ScreeningServiceImplementationTest {
    private final MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
    private final RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
    private final ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
    private final ScreeningServiceInterface underTest = new ScreeningServiceImplementation(screeningRepository,
            movieRepository, roomRepository);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Movie movie = new Movie("Terminator", "Action", 108);
    private final Room room = new Room("Pedersoli", 10, 10);
    private final Screening screening = new Screening(movie,
            room, LocalDateTime.parse("2023-11-20 10:00", formatter));
    private final Screening anotherScreening = new Screening(movie,
            room, LocalDateTime.parse("2023-11-20 20:00", formatter));
    private final Screening overlapingScreening1 = new Screening(movie,
            room, LocalDateTime.parse("2023-11-20 10:30", formatter));
    private final Screening overlapingScreening2 = new Screening(movie,
            room, LocalDateTime.parse("2023-11-20 09:30", formatter));
    private final Screening breakScreening = new Screening(movie,
            room, LocalDateTime.parse("2023-11-20 11:50", formatter));

    @Test
    void testCreateScreeningShouldCreateScreeningWhenAScreeningIsValid() throws DoesNotExists, Overlap, BreakPeriod {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(screening.getMovie(), screening.getRoom(),
                screening.getScreeningDate())).thenReturn(Optional.of(screening));

        // When
        underTest.createScreening(screening.getMovie().getMovieName(),
                screening.getRoom().getRoomName(), screening.getScreeningDate());

        // Then
        verify(screeningRepository).save(screening);
    }

    @Test
    void testCreateScreeningShouldCreateScreeningWhenAnotherScreeningExists()
            throws DoesNotExists, Overlap, BreakPeriod {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(
                screening.getMovie(), screening.getRoom(), screening.getScreeningDate()))
                .thenReturn(Optional.empty());
        when(screeningRepository.findScreeningByRoom(room)).thenReturn(Optional.of(anotherScreening));

        // When
        underTest.createScreening(screening.getMovie().getMovieName(),
                screening.getRoom().getRoomName(), screening.getScreeningDate());

        // Then
        verify(screeningRepository).save(screening);
    }

    @Test
    void testCreateScreeningShouldThrowOverlapWhenAnotherScreeningExistsInThisDateUpper() {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(
                screening.getMovie(), screening.getRoom(), screening.getScreeningDate()))
                .thenReturn(Optional.empty());
        when(screeningRepository.findScreeningByRoom(room)).thenReturn(Optional.of(overlapingScreening1));

        // When
        assertThrows(Overlap.class, () -> underTest.createScreening(screening.getMovie().getMovieName(),
                screening.getRoom().getRoomName(), screening.getScreeningDate()));

        // Then
        verify(screeningRepository, never()).save(screening);
    }

    @Test
    void testCreateScreeningShouldThrowOverlapWhenAnotherScreeningExistsInThisDateLower() {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(
                screening.getMovie(), screening.getRoom(), screening.getScreeningDate()))
                .thenReturn(Optional.empty());
        when(screeningRepository.findScreeningByRoom(room)).thenReturn(Optional.of(overlapingScreening2));

        // When
        assertThrows(Overlap.class, () -> underTest.createScreening(screening.getMovie().getMovieName(),
                screening.getRoom().getRoomName(), screening.getScreeningDate()));

        // Then
        verify(screeningRepository, never()).save(screening);
    }

    @Test
    void testCreateScreeningShouldThrowOverlapWhenThereIsBreakUpper() {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(
                screening.getMovie(), screening.getRoom(), screening.getScreeningDate()))
                .thenReturn(Optional.empty());
        when(screeningRepository.findScreeningByRoom(room)).thenReturn(Optional.of(breakScreening));

        // When
        assertThrows(BreakPeriod.class, () -> underTest.createScreening(screening.getMovie().getMovieName(),
                screening.getRoom().getRoomName(), screening.getScreeningDate()));

        // Then
        verify(screeningRepository, never()).save(screening);
    }

    @Test
    void testDeleteScreeningShouldDeleteScreeningWhenAScreeningIsSaved() throws DoesNotExists {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(screening.getMovie(), screening.getRoom(),
                screening.getScreeningDate())).thenReturn(Optional.of(screening));
        doNothing().when(screeningRepository).delete(screening);

        // When
        underTest.deleteScreening(screening.getMovie().getMovieName(),
                screening.getRoom().getRoomName(), screening.getScreeningDate());

        // Then
        verify(screeningRepository, never()).save(screening);
        verify(screeningRepository).delete(screening);
    }

    @Test
    void testDeleteScreeningShouldNotDeleteScreeningWhenAScreeningIsNotSaved(){
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(screening.getMovie(), screening.getRoom(),
                screening.getScreeningDate())).thenReturn(Optional.empty());

        // When/Then
        assertThrows(DoesNotExists.class, () -> underTest.deleteScreening(screening.getMovie().getMovieName(),
                screening.getRoom().getRoomName(), screening.getScreeningDate()));
        verify(screeningRepository, never()).save(screening);
        verify(screeningRepository, never()).delete(screening);
    }

    @Test
    void testDeleteScreeningShouldReturnDoesNotExistsWhenAMovieAndRoomIsNotSaved(){
        // Given
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(screening.getMovie(), screening.getRoom(),
                screening.getScreeningDate())).thenReturn(Optional.empty());

        // When/Then
        assertThrows(DoesNotExists.class, () -> underTest.deleteScreening(screening.getMovie().getMovieName(),
                screening.getRoom().getRoomName(), screening.getScreeningDate()));
        verify(screeningRepository, never()).save(screening);
        verify(screeningRepository, never()).delete(screening);
    }

    @Test
    void testDeleteScreeningShouldReturnDoesNotExistsWhenARoomIsNotSaved(){
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(screening.getMovie(), screening.getRoom(),
                screening.getScreeningDate())).thenReturn(Optional.empty());

        // When/Then
        assertThrows(DoesNotExists.class, () -> underTest.deleteScreening(screening.getMovie().getMovieName(),
                screening.getRoom().getRoomName(), screening.getScreeningDate()));
        verify(screeningRepository, never()).save(screening);
        verify(screeningRepository, never()).delete(screening);
    }

    @Test
    void testDeleteScreeningShouldReturnDoesNotExistsWhenAMovieIsNotSaved(){
        // Given
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        when(screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(screening.getMovie(), screening.getRoom(),
                screening.getScreeningDate())).thenReturn(Optional.empty());

        // When/Then
        assertThrows(DoesNotExists.class, () -> underTest.deleteScreening(screening.getMovie().getMovieName(),
                screening.getRoom().getRoomName(), screening.getScreeningDate()));
        verify(screeningRepository, never()).save(screening);
        verify(screeningRepository, never()).delete(screening);
    }

    @Test
    void testListScreeningsShouldReturnScreeningsListWhenAScreeningIsSaved() {
        // Given
        Mockito.when(screeningRepository.findAll()).thenReturn(Collections.singletonList(screening));

        // When
        List<ScreeningDto> actual = underTest.listScreenings();

        // Then
        Mockito.verify(screeningRepository).findAll();
        assertEquals(1, actual.size());
        assertEquals(screening.getMovie().getMovieName(), actual.get(0).getMovieDto().getMovieName());
        assertEquals(screening.getRoom().getRoomName(), actual.get(0).getRoomDto().getRoomName());
        assertEquals(screening.getScreeningDate(), actual.get(0).getScreeningDate());
    }

    @Test
    void testRoomListShouldReturnEmptyListWhenRoomListIsEmpty() {
        // Given

        // When
        List<ScreeningDto> actual = underTest.listScreenings();

        // Then
        assertEquals(emptyList(), actual);
    }

}
