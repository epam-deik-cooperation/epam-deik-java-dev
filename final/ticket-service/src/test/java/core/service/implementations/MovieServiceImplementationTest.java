package core.service.implementations;

import com.epam.training.ticketservice.core.model.Movie;
import com.epam.training.ticketservice.core.repository.MovieRepository;
import com.epam.training.ticketservice.core.service.implementations.MovieServiceImplementation;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

public class MovieServiceImplementationTest {
    Movie movie = new Movie("Terminator", "Action", 108);
    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieServiceImplementation testMovieServiceImplementation = new MovieServiceImplementation(movieRepository);

    @Test
    void testMovieCreateShouldStoreTheGivenMovieWhenTheInputMovieIsNotExisting(){
        //Given
        when(movieRepository.save(movie)).thenReturn(movie);

        //When
        String actual = testMovieServiceImplementation.movieCreate(movie.getMovieName(), movie.getMovieGenre(),
                movie.getWatchTime());

        //Then
        assertEquals("The movie created successfully", actual);
        verify(movieRepository).save(movie);
    }

    @Test
    void testMovieCreateShouldNotStoreTheGivenMovieWhenTheInputMovieIsExisting() {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));

        // When
        String actual = testMovieServiceImplementation.movieCreate(
                movie.getMovieName(), movie.getMovieGenre(), movie.getWatchTime());

        // Then
        assertEquals("The movie is already existed", actual);
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void testMovieUpdateShouldNotUpdateTheGivenMovieWhenTheStoredMovieIsNotExisting() {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.empty());

        // When
        String actual = testMovieServiceImplementation.movieUpdate(movie.getMovieName(),
                "NewGenre", 120);

        // Then
        assertEquals("The movie does not exists", actual);
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void testMovieUpdateShouldUpdateTheGivenMovieWhenTheStoredMovieIsExisting() {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);

        // When
        String actual = testMovieServiceImplementation.movieUpdate(movie.getMovieName(), "Drama", 120);

        // Then
        assertEquals("The movie was updated successfully", actual);
        verify(movieRepository).save(any(Movie.class));
    }

    @Test
    void testMovieDeleteShouldNotDeleteTheGivenMovieWhenTheStoredMovieIsNotExisting() {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.empty());

        // When
        String actual = testMovieServiceImplementation.movieDelete(movie.getMovieName());

        // Then
        assertEquals("The movie does not exists", actual);
        verify(movieRepository, never()).save(any(Movie.class));
        verify(movieRepository, never()).delete(any(Movie.class));
    }

    @Test
    void testMovieDeleteShouldDeleteTheGivenMovieWhenTheStoredMovieIsExisting() {
        // Given
        when(movieRepository.findByMovieName(movie.getMovieName())).thenReturn(Optional.of(movie));
        doNothing().when(movieRepository).delete(any(Movie.class));

        // When
        String actual = testMovieServiceImplementation.movieDelete(movie.getMovieName());

        // Then
        assertEquals("The movie was deleted successfully", actual);
        verify(movieRepository, never()).save(any(Movie.class));
        verify(movieRepository).delete(movie);
    }

    @Test
    void testMovieListShouldListTheMovies() {
        // Given
        List<Movie> movieList = Collections.singletonList(movie);
        when(movieRepository.findAll()).thenReturn(movieList);

        // When
        String actual = testMovieServiceImplementation.movieList();

        // Then
        assertEquals("Terminator (Action, 108 minutes)", actual);
        verify(movieRepository, never()).save(any(Movie.class));
    }
}
