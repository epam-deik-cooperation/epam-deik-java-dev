package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.businesslogic.account.AccountService;
import com.epam.training.ticketservice.businesslogic.movie.MovieService;
import com.epam.training.ticketservice.businesslogic.room.RoomService;
import com.epam.training.ticketservice.businesslogic.screening.ScreeningService;
import com.epam.training.ticketservice.models.movie.Movie;
import com.epam.training.ticketservice.models.room.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ScreeningCommandsTest {

    MovieService movieService;
    RoomService roomService;
    ScreeningService screeningService;
    AccountService accountService;
    ScreeningCommands underTest;

    @BeforeEach
    public void init(){
        movieService = Mockito.mock(MovieService.class);
        roomService = Mockito.mock(RoomService.class);
        screeningService = Mockito.mock(ScreeningService.class);
        accountService = Mockito.mock(AccountService.class);
        underTest = new ScreeningCommands(screeningService, roomService, movieService, accountService);
    }

    @Test
    public void createScreeningShouldCallCorrectServiceMethod(){
        var movie = new Movie("movieName", "genre", 100);
        var room = new Room("roomName", 10, 20);
        Mockito.when(movieService.getByName("movieName")).thenReturn(Optional.of(movie));
        Mockito.when(roomService.getByName("roomName")).thenReturn(Optional.of(room));
        underTest.createScreening("movieName", "roomName", "2021-12-07 23:30");
        Mockito.verify(screeningService).createScreening(movie, room, LocalDateTime.parse("2021-12-07 23:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    @Test
    public void deleteScreeningShouldCallCorrectServiceMethod(){
        var movie = new Movie("movieName", "genre", 100);
        var room = new Room("roomName", 10, 20);
        Mockito.when(movieService.getByName("movieName")).thenReturn(Optional.of(movie));
        Mockito.when(roomService.getByName("roomName")).thenReturn(Optional.of(room));
        underTest.deleteScreening("movieName", "roomName", "2021-12-07 23:30");
        Mockito.verify(screeningService).deleteScreening(movie, room, LocalDateTime.parse("2021-12-07 23:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}
