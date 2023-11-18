package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.service.implementations.MovieServiceImplementation;
import com.epam.training.ticketservice.core.service.implementations.UserServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommand {
    private final MovieServiceImplementation movieServiceImplementation;
    private final UserServiceImplementation userServiceImplementation;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Creates a movie")
    public String createMovie(String movieName, String genre, int length) {
        return movieServiceImplementation.movieCreate(movieName, genre, length);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Updates an existing movie")
    public String movieUpdater(String movieName, String genre, int length) {
        return movieServiceImplementation.movieUpdate(movieName, genre, length);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "Deletes an existing movie")
    public String movieDeleter(String movieName) {
        return movieServiceImplementation.movieDelete(movieName);
    }

    @ShellMethod(key = "list movies", value = "Lists all created movies")
    public String listMovies() {
        return movieServiceImplementation.movieList();
    }

    public Availability isAvailable() {
        if (userServiceImplementation.getLoggedInUser() != null) {
            return Availability.available();
        } else {
            return Availability.unavailable("You are not authorized");
        }
    }
}
