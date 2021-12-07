package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.businesslogic.account.AccountService;
import com.epam.training.ticketservice.businesslogic.movie.MovieService;
import com.epam.training.ticketservice.businesslogic.room.RoomService;
import com.epam.training.ticketservice.businesslogic.screening.ScreeningService;
import com.epam.training.ticketservice.models.movie.Movie;
import com.epam.training.ticketservice.models.room.Room;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ShellComponent
public class ScreeningCommands {

    ScreeningService screeningService;
    RoomService roomService;
    MovieService movieService;
    AccountService accountService;

    private Availability isAdmin() {
        return Authentication.admin(accountService.getCurrentUser());
    }

    public ScreeningCommands(ScreeningService screeningService, RoomService roomService, MovieService movieService,
                             AccountService accountService) {
        this.screeningService = screeningService;
        this.roomService = roomService;
        this.movieService = movieService;
        this.accountService = accountService;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create screening", value = "Create a new screening.")
    public String createScreening(String movieName, String roomName, String dateTimeText) {
        var movie = movieService.getByName(movieName);
        var room = roomService.getByName(roomName);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeText, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if (movie.isPresent() && room.isPresent()) {
            return screeningService.createScreening(movie.get(), room.get(), dateTime);
        } else {
            return "Can't find the provided movie or room";
        }
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete screening", value = "Delete an existing screening.")
    public void deleteScreening(String movieName, String roomName, String dateTimeText) {
        var movie = movieService.getByName(movieName);
        var room = roomService.getByName(roomName);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeText, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if (movie.isPresent() && room.isPresent()) {
            screeningService.deleteScreening(movie.get(), room.get(), dateTime);
        }
    }

    @ShellMethod(key = "list screenings", value = "List existing screenings.")
    public String listScreening() {
        var screenings = screeningService.listScreenings();
        if (screenings.isEmpty()) {
            return "There are no screenings";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (var s : screenings) {
            Movie m = s.getMovie();
            Room r = s.getRoom();
            stringBuilder.append(m.getName())
                    .append(" (")
                    .append(m.getGenre())
                    .append(", ")
                    .append(m.getMinutes())
                    .append(" minutes), screened in room ")
                    .append(r.getName())
                    .append(", at ")
                    .append(s.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                    .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
