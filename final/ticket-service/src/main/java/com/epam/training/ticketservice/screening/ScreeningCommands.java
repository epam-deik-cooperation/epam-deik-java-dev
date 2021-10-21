package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.exception.DateConflictException;
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
        try {
            screeningService.createScreening(Screening.builder()
                    .movie(movieService.findByTitle(movieTitle))
                    .room(roomService.findByName(roomName))
                    .date(date)
                    .build());
        } catch (DateConflictException e) {
            return e.getMessage();
        }

        return "Successfully created screening";
    }

    public String delete(String movieTitle, String roomName, String startDate) {
        /*
            TODO:
                - 'delete screening' command implementation
        */

        return "";
    }

    @ShellMethod(value = "list movies", key = "list screenings")
    public void list() {
        if (!screeningService.getAllScreenings().isEmpty()) {
            screeningService.getAllScreenings().forEach(System.out::println);
        } else {
            System.out.println("There are no screenings");
        }
    }


}
