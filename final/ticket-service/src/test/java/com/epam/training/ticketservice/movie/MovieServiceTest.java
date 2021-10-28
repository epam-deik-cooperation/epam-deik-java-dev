package com.epam.training.ticketservice.movie;


import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    private Movie testMovie;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    MovieService movieService;

    @BeforeEach
    void setUp() {
        testMovie = Movie.builder()
                .title("test")
                .genre("test")
                .length(1)
                .build();
    }

    @Test
    public void testGetAllMoviesShouldReturnListOfMovies() {

        //Given
        List<Movie> expectedList = List.of(testMovie);

        //When
        when(movieRepository.findAll()).thenReturn(expectedList);
        List<Movie> actualList = movieService.getAllMovies();

        //Then
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testFindByTitle() {

        // Given


        // When
        when(movieRepository.findByTitleContainingIgnoreCase(testMovie.getTitle())).thenReturn(testMovie);
        Movie actualMovie = movieService.findByTitle(testMovie.getTitle());

        // Then
        assertEquals(testMovie, actualMovie);
    }

    @Test
    public void testCreateMovie() throws AlreadyExistsException {

        //Given

        //When
        movieService.createMovie(testMovie);

        //Then
        verify(movieRepository, times(1)).save(testMovie);
    }

    @Test
    public void testCreateMovieShouldThrowAlreadyExistExceptionIfMovieAlreadyExists() {

        // Given


        // When
        when(movieRepository.existsByTitleContainingIgnoreCase(anyString())).thenReturn(true);

        // Then
        assertThrows(AlreadyExistsException.class, () -> movieService.createMovie(testMovie));
        verify(movieRepository, times(0)).save(testMovie);
    }

    @Test
    public void testUpdateMovie() throws NotFoundException {

        //Given


        //When
        when(movieRepository.existsByTitleContainingIgnoreCase(testMovie.getTitle())).thenReturn(true);
        movieService.updateMovie(testMovie);

        //Then
        verify(movieRepository, times(1)).update(testMovie.getTitle(),
                testMovie.getGenre(),
                testMovie.getLength());
    }

    @Test
    public void testUpdateMovieShouldThrowNotFoundExceptionWhenExistByTitleReturnsFalse() {

        //Given


        //When
        when(movieRepository.existsByTitleContainingIgnoreCase(testMovie.getTitle())).thenReturn(false);

        //Then
        assertThrows(NotFoundException.class, () -> movieService.updateMovie(testMovie));
        verify(movieRepository, times(0)).update(testMovie.getTitle(),
                testMovie.getGenre(),
                testMovie.getLength());
    }

    @Test
    public void testDeleteMovie() throws NotFoundException {

        //Given


        //When
        when(movieRepository.existsByTitleContainingIgnoreCase(testMovie.getTitle())).thenReturn(true);
        movieService.deleteMovie(testMovie.getTitle());

        //Then
        verify(movieRepository, times(1)).deleteByTitleContainingIgnoreCase(testMovie.getTitle());
    }

    @Test
    public void testDeleteMovieShouldThrowNotFoundExceptionWhenExistByTitleReturnsFalse() {

        //Given


        //When
        when(movieRepository.existsByTitleContainingIgnoreCase(testMovie.getTitle())).thenReturn(false);

        //Then
        assertThrows(NotFoundException.class, () -> movieService.deleteMovie(testMovie.getTitle()));
        verify(movieRepository, times(0)).deleteByTitleContainingIgnoreCase(testMovie.getTitle());
    }


}
