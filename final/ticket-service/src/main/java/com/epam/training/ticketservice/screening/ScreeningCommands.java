package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.exception.DateConflictException;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.room.RoomService;
import javassist.NotFoundException;
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

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @ShellMethod(value = "format: create screening movieTitle roomName startDate", key = "create screening")
    public String create(String movieTitle, String roomName, String startDate) {

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

    @ShellMethod(value = "format: delete screening movieTitle roomName startDate", key = "delete screening")
    public String delete(String movieTitle, String roomName, String startDate) {

        LocalDateTime date = LocalDateTime.parse(startDate, formatter);
        try {
            screeningService.deleteScreening(movieTitle, roomName, date);
        } catch (NotFoundException e) {
            return e.getMessage();
        }

        return "Successfully deleted screening";
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
