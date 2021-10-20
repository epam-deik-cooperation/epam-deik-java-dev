package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ShellComponent
@RequiredArgsConstructor
public class ScreeningCommands {

    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final RoomService roomService;

    @ShellMethod(value = "format: create screening movieTitle roomName startDate", key = "create screening")
    public String create(String movieTitle, String roomName, String startDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.parse(startDate, formatter);

        screeningService.createScreening(Screening.builder()
                .movie(movieService.findByTitle(movieTitle))
                .room(roomService.findByName(roomName))
                .date(date)
                .build());

        return "Successfully created screening";
    }

    public String delete(String movieTitle, String roomName, String startDate) {
        /*
            TODO:
                - 'delete screening' command implementation
        */

        return "";
    }

    public String list() {
        /*
            TODO:
                - 'list screenings' command implementation
        */

        return "";
    }


}
