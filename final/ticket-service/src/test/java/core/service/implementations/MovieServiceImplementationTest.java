package core.service.implementations;

import com.epam.training.ticketservice.core.dto.MovieDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.model.Movie;
import com.epam.training.ticketservice.core.repository.MovieRepository;
import com.epam.training.ticketservice.core.service.implementations.MovieServiceImplementation;
import com.epam.training.ticketservice.core.service.interfaces.MovieServiceInterface;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;

public class MovieServiceImplementationTest {
    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieServiceInterface underTest = new MovieServiceImplementation(movieRepository);
    private final Movie movie = new Movie("Terminator", "Action", 108);
    private final Movie movieUpdated = new Movie("Terminator", "Drama", 120);
    @Test
    void testMovieCreateShouldSaveMovieWhenMovieIsNotExisting() throws AlreadyExists {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.empty());

        // When
        underTest.movieCreate(movie.getMovieName(), movie.getMovieGenre(), movie.getWatchTime());

        // Then
        verify(movieRepository).save(any(Movie.class));
    }

    @Test
    void testMovieCreateShouldStoreTheGivenMovieWhenTheInputMovieIsExisting() {
        //Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));

        // When/Then
        assertThrows(AlreadyExists.class, () -> underTest.movieCreate(movie.getMovieName(),
                movie.getMovieGenre(), movie.getWatchTime()));
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void testMovieUpdateShouldUpdateTheGivenMovieWhenTheStoredMovieIsExisting() throws DoesNotExists {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);

        // When
        underTest.movieUpdate(movie.getMovieName(), movieUpdated.getMovieGenre(), movieUpdated.getWatchTime());

        // Then
        verify(movieRepository).save(movie);
    }

    @Test
    void testMovieUpdateShouldNotUpdateTheGivenMovieWhenTheStoredMovieIsNotExisting() {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.empty());

        // When/Then
        assertThrows(DoesNotExists.class, () -> underTest.movieUpdate(movie.getMovieName(),
                movieUpdated.getMovieGenre(), movieUpdated.getWatchTime()));
        verify(movieRepository, never()).save(movie);
    }

    @Test
    void testMovieDeleteShouldDeleteTheGivenMovieWhenTheStoredMovieIsExisting() throws DoesNotExists {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        doNothing().when(movieRepository).delete(movie);

        // When
        underTest.movieDelete(movie.getMovieName());

        // Then
        verify(movieRepository, never()).save(movie);
        verify(movieRepository).delete(movie);
    }

    @Test
    void testMovieDeleteShouldNotDeleteTheGivenMovieWhenTheStoredMovieIsNotExisting() {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.empty());

        // When/Then
        assertThrows(DoesNotExists.class, () -> underTest.movieDelete(movie.getMovieName()));
        verify(movieRepository, never()).save(movie);
        verify(movieRepository, never()).delete(movie);
    }

    @Test
    void testMovieListShouldReturnMovieListWhenAMovieIsSaved() {
        // Given
        when(movieRepository.findAll()).thenReturn(Collections.singletonList(movie));

        // When
        List<MovieDto> actual = underTest.movieList();

        // Then
        verify(movieRepository).findAll();
        assertEquals(1, actual.size());
        assertEquals(movie.getMovieName(), actual.get(0).getMovieName());
        assertEquals(movie.getMovieGenre(), actual.get(0).getMovieGenre());
        assertEquals(movie.getWatchTime(), actual.get(0).getWatchTime());
    }

    @Test
    void testMovieListShouldReturnNoMoviesWhenMovieListIsEmpty() {
        // Given

        // When
        List<MovieDto> actual = underTest.movieList();

        // Then
        assertEquals(emptyList(), actual);
    }
}