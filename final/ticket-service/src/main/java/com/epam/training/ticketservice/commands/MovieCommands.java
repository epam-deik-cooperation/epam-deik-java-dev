package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.businesslogic.account.AccountService;
import com.epam.training.ticketservice.businesslogic.movie.MovieService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class MovieCommands {

    MovieService movieService;
    AccountService accountService;

    private Availability isAdmin() {
        return Authentication.admin(accountService.getCurrentUser());
    }

    public MovieCommands(MovieService movieService, AccountService accountService) {
        this.movieService = movieService;
        this.accountService = accountService;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create movie", value = "Create a movie.")
    public void createMovie(String name, String genre, int length) {
        movieService.createMovie(name, genre, length);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "update movie", value = "Update an existing movie.")
    public void updateMovie(String name, String genre, int length) {
        movieService.updateMovie(name, genre, length);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete movie", value = "Delete an existing movie.")
    public void deleteMovie(String name) {
        movieService.deleteMovie(name);
    }

    @ShellMethod(key = "list movies", value = "List existing movies.")
    public String listMovies() {
        var movies = movieService.listMovies();
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (var m : movies) {
            stringBuilder.append(m.getName())
                    .append(" (")
                    .append(m.getGenre())
                    .append(", ")
                    .append(m.getMinutes())
                    .append(" minutes)")
                    .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}