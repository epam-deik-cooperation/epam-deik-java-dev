package com.epam.training.ticketservice.businesslogic.movie;

import com.epam.training.ticketservice.models.movie.Movie;
import com.epam.training.ticketservice.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class MovieServiceTest {

    MovieService underTest;
    MovieRepository movieRepository;

    @BeforeEach
    public void init() {
        movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new MovieService(movieRepository);
    }

    @Test
    public void createMovieShouldCallRepository(){
        underTest.createMovie("movieName", "genreName", 60);
        Mockito.verify(movieRepository).save(new Movie("movieName", "genreName", 60));
    }

    @Test
    public void updateMovieShouldCallRepository(){
        Mockito.when(movieRepository.findByName("movieName")).thenReturn(Optional.of(new Movie("movieName", "GenreName", 60)));
        underTest.updateMovie("movieName", "newGenreName", 60);
        Mockito.verify(movieRepository).save(new Movie("movieName", "newGenreName", 60));
    }

    @Test
    public void deleteMovieShouldCallRepository(){
        Mockito.when(movieRepository.findByName("movieName")).thenReturn(Optional.of(new Movie("movieName", "genreName", 60)));
        underTest.deleteMovie("movieName");
        Mockito.verify(movieRepository).delete(new Movie("movieName", "genreName", 60));
    }

}
