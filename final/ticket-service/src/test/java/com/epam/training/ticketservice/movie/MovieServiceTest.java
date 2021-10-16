package com.epam.training.ticketservice.movie;


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
    public void testCreateMovie() {

        //Given


        //When
        movieService.createMovie(testMovie);

        //Then
        verify(movieRepository, times(1)).save(testMovie);
    }

    @Test
    public void testUpdateMovie() throws NotFoundException {

        //Given


        //When
        when(movieRepository.existsByTitle(testMovie.getTitle())).thenReturn(true);
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
        when(movieRepository.existsByTitle(testMovie.getTitle())).thenReturn(false);

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
        when(movieRepository.existsByTitle(testMovie.getTitle())).thenReturn(true);
        movieService.deleteMovie(testMovie.getTitle());

        //Then
        verify(movieRepository, times(1)).deleteByTitle(testMovie.getTitle());
    }

    @Test
    public void testDeleteMovieShouldThrowNotFoundExceptionWhenExistByTitleReturnsFalse() {

        //Given


        //When
        when(movieRepository.existsByTitle(testMovie.getTitle())).thenReturn(false);

        //Then
        assertThrows(NotFoundException.class, () -> movieService.deleteMovie(testMovie.getTitle()));
        verify(movieRepository, times(0)).deleteByTitle(testMovie.getTitle());
    }


}
