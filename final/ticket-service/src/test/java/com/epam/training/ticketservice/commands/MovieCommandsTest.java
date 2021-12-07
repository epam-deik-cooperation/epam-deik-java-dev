package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.businesslogic.account.AccountService;
import com.epam.training.ticketservice.businesslogic.movie.MovieService;
import com.epam.training.ticketservice.models.movie.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class MovieCommandsTest {

    MovieService movieService;
    AccountService accountService;
    MovieCommands underTest;

    @BeforeEach
    public void init() {
        movieService = Mockito.mock(MovieService.class);
        accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommands(movieService, accountService);
    }

    @Test
    public void stringRepresentationOfMovieShouldDisplayCorrectDetailsOfMovie(){
        var list = List.of(new Movie("testMovie", "testGenre", 60));
        Mockito.when(movieService.listMovies()).thenReturn(list);
        String returnValue = underTest.listMovies();
        Assertions.assertEquals("testMovie (testGenre, 60 minutes)" + System.lineSeparator(), returnValue);
    }

    @Test
    public void createMovieShouldCallCorrectServiceMethod(){
        underTest.createMovie("testMovie", "testGenre", 45);
        Mockito.verify(movieService).createMovie("testMovie", "testGenre", 45);
    }

    @Test
    public void updateMovieShouldCallCorrectServiceMethod(){
        underTest.updateMovie("testMovie", "testGenre", 30);
        Mockito.verify(movieService).updateMovie("testMovie", "testGenre", 30);
    }

    @Test
    public void deleteMovieShouldCallCorrectServiceMethod(){
        underTest.deleteMovie("movieName");
        Mockito.verify(movieService).deleteMovie("movieName");
    }
}
