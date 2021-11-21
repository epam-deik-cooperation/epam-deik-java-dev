package com.epam.training.ticketservice.movie;


import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieCommandsTest {


    @Mock
    MovieService movieService;

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieCommands movieCommands;

    private Movie movie;
    private String title;
    private String genre;
    private int length;


    @BeforeEach
    private void setUp() {
        title = "test";
        genre = "test";
        length = 100;

        movie = Movie.builder()
                .title(title)
                .genre(genre)
                .length(length)
                .build();
    }


    @Test
    public void testCreateMovie() throws AlreadyExistsException {

        // Given


        // When
        when(movieService.mapToMovie(title, genre, length)).thenReturn(movie);
        movieCommands.createMovie(title, genre, length);

        // Then
        verify(movieService, times(1)).createMovie(any(Movie.class));
    }

    @Test
    public void testCreateMovieShouldNotSaveMovieIfAlreadyExistsExceptionIsCaught()
            throws AlreadyExistsException {

        // Given


        // When
        when(movieService.mapToMovie(title, genre, length)).thenReturn(movie);
        doThrow(AlreadyExistsException.class).when(movieService).createMovie(any(Movie.class));
        movieCommands.createMovie(title, genre, length);

        // Then
        verify(movieRepository, times(0)).save(any(Movie.class));
    }


    @Test
    public void testUpdateMovie() throws NotFoundException {

        // Given


        // When
        when(movieService.mapToMovie(title, genre, length)).thenReturn(movie);
        movieCommands.updateMovie(title, genre, length);

        // Then
        verify(movieService, times(1)).updateMovie(any(Movie.class));
    }

    @Test
    public void testUpdateMovieShouldNotUpdateMovieIfAlreadyExistsExceptionIsCaught()
            throws NotFoundException {

        // Given


        // When
        when(movieService.mapToMovie(title, genre, length)).thenReturn(movie);
        doThrow(NotFoundException.class).when(movieService).updateMovie(any(Movie.class));
        movieCommands.updateMovie(title, genre, length);

        // Then
        verify(movieRepository, times(0)).update(anyString(), anyString(), anyInt());
    }

    @Test
    public void testDeleteMovie() throws NotFoundException {

        // Given
        String title = "test";

        // When
        movieCommands.deleteMovie(title);

        // Then
        verify(movieService, times(1)).deleteMovie(title);
    }

    @Test
    public void testDeleteMovieShouldNotDeleteMovieIfNotFoundExceptionIsCaught()
            throws NotFoundException {

        // Given
        String title = "test";

        // When
        doThrow(NotFoundException.class).when(movieService).deleteMovie(title);
        movieCommands.deleteMovie(title);

        // Then
        verify(movieRepository, times(0)).deleteByTitleContainingIgnoreCase(title);
    }

    @Test
    public void testListMoviesShouldReturnExpectedStringIfNoMoviesAreFound() {

        // Given
        String expectedString = "There are no movies at the moment";

        // When
        when(movieService.getAllMovies()).thenReturn(Collections.emptyList());
        String actualString = movieCommands.listMovies();

        // Then
        assertEquals(expectedString, actualString);
    }

    @Test
    public void testListMoviesShouldReturnStringWithFoundMovies() {

        // Given
        Movie testMovie1 = Movie.builder()
                .title("test")
                .genre("test")
                .length(100)
                .build();


        Movie testMovie2 = Movie.builder()
                .title("test2")
                .genre("test2")
                .length(100)
                .build();

        String expectedString = testMovie1 + "\n" + testMovie2;

        // When
        when(movieService.getAllMovies()).thenReturn(List.of(testMovie1, testMovie2));
        String actualString = movieCommands.listMovies();

        // Then
        assertEquals(expectedString, actualString);
    }


}
