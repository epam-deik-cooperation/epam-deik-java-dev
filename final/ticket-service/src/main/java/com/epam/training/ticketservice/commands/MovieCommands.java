package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.logsystem.LogSystem;
import com.epam.training.ticketservice.models.moviemodel.Movie;
import com.epam.training.ticketservice.models.moviemodel.Movies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class MovieCommands {
    private final Movies movies;
    private final LogSystem logSystem;

    @Autowired
    public MovieCommands(Movies movies, LogSystem logSystem) {
        this.movies = movies;
        this.logSystem = logSystem;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Creates a movie")
    public void createMovie(String movieName, String genre, int length){
        movies.append(movieName, genre, length);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Updates an existing movie")
    public String movieUpdater(String movieName, String genre, int length){
        for (Movie movie : movies.getMovies()) {
            if (movie.getName().equals(movieName)){
                movie.setGenre(genre);
                movie.setLengthInTime(length);
                return "Movie successfully updated.";
            }
        }
        return "Movie was not found.";
    }

    @ShellMethod(key = "list movies", value = "Lists all created movies")
    public String listMovies(){
        return movies.getMovies().toString();
    }

    public Availability isAvailable(){
        if(logSystem.isLoggedIn()){
            return Availability.available();
        }else return Availability.unavailable("You are no an admin.");
    }
}
