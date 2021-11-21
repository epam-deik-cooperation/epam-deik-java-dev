package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.SecuredCommands;
import com.epam.training.ticketservice.exception.ConflictException;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.room.RoomService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ShellComponent
@RequiredArgsConstructor
public class ScreeningCommands extends SecuredCommands {

    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final RoomService roomService;


    @ShellMethod(value = "create screening movieTitle roomName startDate", key = "create screening")
    @ShellMethodAvailability("isAccountAdmin")
    public String createScreening(String movieTitle, String roomName, String startDate) {

        try {
            Screening screening = screeningService.mapToScreening(movieTitle, roomName, startDate);
            screeningService.createScreening(screening);

        } catch (ConflictException | NotFoundException e) {
            return e.getMessage();
        }

        return "Successfully created screening";
    }

    @ShellMethod(value = "delete screening movieTitle roomName startDate", key = "delete screening")
    @ShellMethodAvailability("isAccountAdmin")
    public String deleteScreening(String movieTitle, String roomName, String startDate) {

        try {
            Screening screening = screeningService.mapToScreening(movieTitle, roomName, startDate);
            screeningService.deleteScreening(screening);

        } catch (NotFoundException e) {
            return e.getMessage();
        }

        return "Successfully deleted screening";
    }

    @ShellMethod(value = "list screenings", key = "list screenings")
    public String listScreening() {

        StringBuilder sb = new StringBuilder();

        if (!screeningService.getAllScreenings().isEmpty()) {
            screeningService.getAllScreenings().forEach(x -> sb.append(x).append("\n"));
            sb.setLength(sb.length() - 1);
            return sb.toString();
        } else {
            return "There are no screenings";
        }
    }


}
