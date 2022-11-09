package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.entity.Movie;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.repository.MovieRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;


class MovieServiceImplTest {

    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieService underTest = new MovieServiceImpl(movieRepository);

    @Test
    void testGetMovieByNameShouldReturnNull(){
        //Given
        Optional<MovieDto> expected = Optional.empty();

        //When
        Optional<MovieDto> actual = underTest.getMovieByName("Test");

        //Then
        assertEquals(actual,expected);
    }

    @Test
    void testGetMovieByNameShouldReturnValue(){
        //Given
        Movie movie = new Movie("Test","theme",10);
        Optional<Movie> expected = Optional.of(movie);

        when(movieRepository.findByName("Test")).thenReturn(Optional.of(movie));

        //When
        Optional<MovieDto> actual = underTest.getMovieByName("Test");

        //Then
        assertEquals(expected.get().getName(), actual.get().getName());
        assertEquals(expected.get().getTheme(), actual.get().getTheme());
        assertEquals(expected.get().getLength(), actual.get().getLength());
        verify(movieRepository).findByName("Test");
    }

    @Test
    void testDeleteMovieShouldReturnFalse(){
        //Given
        MovieDto movie = new MovieDto("Test", "theme", 10);
        boolean expected = false;

        //When
        boolean actual = underTest.deleteMovie(movie);

        //Then
        assertEquals(expected,actual);
    }

    @Test
    void testGetMovieListShouldReturnEmpty(){
        //Given
        List<MovieDto> expected = List.of();

        //When
        List<MovieDto> actual = underTest.getMovieList();

        //Then
        assertEquals(expected,actual);
    }
    @Test
    void testGetMovieListShouldReturnValues(){
        //Given
        Movie movie = new Movie("Test","theme",10);
        List<Movie> expected = List.of(movie);

        when(movieRepository.findAll())
                .thenReturn(List.of(movie));

        //When
        List<MovieDto> actual = underTest.getMovieList();

        //Then
        assertEquals(expected.size(), actual.size());
        for(int i=0; i<expected.size();i++){
            assertEquals(expected.get(i).getName(),actual.get(i).getName());
            assertEquals(expected.get(i).getTheme(),actual.get(i).getTheme());
            assertEquals(expected.get(i).getLength(),actual.get(i).getLength());
        }
    }
}