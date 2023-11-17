package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.service.implementations.MovieServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommand {
    private final MovieServiceImplementation movieServiceImplementation;

    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Creates a movie")
    public String createMovie(String movieName, String genre, int length){
        return movieServiceImplementation.movieCreate(movieName, genre, length);
    }

    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Updates an existing movie")
    public String movieUpdater(String movieName, String genre, int length){
        return movieServiceImplementation.movieUpdate(movieName, genre, length);
    }

    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "Deletes an existing movie")
    public String movieDeleter(String movieName){
        return movieServiceImplementation.movieDelete(movieName);
    }

    @ShellMethod(key = "list movies", value = "Lists all created movies")
    public String listMovies(){
        return movieServiceImplementation.movieList().toString();
    }

    public Availability isAvailable(){
        return null;
    }
}
