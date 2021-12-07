package com.epam.training.ticketservice.businesslogic.screening;
import com.epam.training.ticketservice.models.movie.Movie;
import com.epam.training.ticketservice.models.room.Room;
import com.epam.training.ticketservice.models.screening.Screening;
import com.epam.training.ticketservice.repositories.ScreeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ScreeningServiceTest {

    ScreeningRepository screeningRepository;
    ScreeningService underTest;

    @BeforeEach
    public void init(){
        screeningRepository = Mockito.mock(ScreeningRepository.class);
        underTest = new ScreeningService(screeningRepository);
        var list = List.of(new Screening(new Movie("movieName1", "genreName", 60),
                new Room("room1", 42, 10),
                LocalDateTime.parse("2021-12-07 23:40", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        Mockito.when(screeningRepository.findAll()).thenReturn(list);

    }

    @Test
    public void createScreeningShouldCallRepository(){
        underTest.createScreening(new Movie("movieName2", "genreName", 60),
                new Room("room1", 42, 10),
                LocalDateTime.parse("2021-12-07 23:40", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    @Test
    public void createScreeningShouldReturnWithMessageIfScreeningOverlaps(){
        underTest.createScreening(new Movie("movieName2", "genreName", 60),
                new Room("room1", 42, 10),
                LocalDateTime.parse("2021-12-07 23:40", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    @Test
    public void createScreeningShouldReturnWithMessageIfScreeningOverlapsBreakTime(){
        underTest.createScreening(new Movie("movieName2", "genreName", 60),
                new Room("room1", 42, 10),
                LocalDateTime.parse("2021-12-07 23:40", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }


}
