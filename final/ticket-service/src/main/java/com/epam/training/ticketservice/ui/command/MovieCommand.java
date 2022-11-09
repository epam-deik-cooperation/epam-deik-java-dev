package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class MovieCommand {

    private final MovieService movieService;
    private final UserService userService;

    @ShellMethod(key = "list movies", value = "List all movies")
    public String getMovieList() {
        List<MovieDto> movies = movieService.getMovieList();
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            StringBuilder result = new StringBuilder();
            movies.forEach(movie -> {
                result.append(movie.toString() + "\n");
            });
            return result.toString().trim();
        }
    }

    @ShellMethod(key = "create movie", value = "Creates a movie")
    @ShellMethodAvailability("isAvailable")
    public String createMovie(String name, String theme, int length) {
        Optional<MovieDto> movie = movieService.getMovieByName(name);
        if (movie.isPresent()) {
            return "Movie " + name + " already exists, try \"update movie\" instead";
        } else {
            MovieDto newMovie = MovieDto.builder().name(name).theme(theme).length(length).build();
            movieService.createMovie(newMovie);
            return "Movie " + name + " created successfully";
        }
    }

    @ShellMethod(key = "delete movie", value = "Deletes a movie if exists")
    @ShellMethodAvailability("isAvailable")
    public String deleteMovie(String name) {
        Optional<MovieDto> movie = movieService.getMovieByName(name);
        if (movie.isEmpty()) {
            return "Movie " + name + " doesn't exist";
        } else {
            if (movieService.deleteMovie(movie.get())) {
                return "Movie " + name + " deleted successfully";
            }
            return "Deleting " + name + " failed";
        }
    }

    @ShellMethod(key = "update movie", value = "Updates an existing movie")
    @ShellMethodAvailability("isAvailable")
    public String updateMovie(String name, String theme, int length) {
        Optional<MovieDto> movie = movieService.getMovieByName(name);

        if (movie.isEmpty()) {
            return "Movie " + name + " doesn't exist";
        } else {
            if (movieService.deleteMovie(movie.get())) {
                MovieDto newMovie = MovieDto.builder().name(name).theme(theme).length(length).build();
                movieService.createMovie(newMovie);
                return "Movie " + name + " updated successfully";
            }
            return "Updating " + name + " failed";
        }
    }


    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        if (user.isPresent() && user.get().getRole() == User.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You are not an admin!");
    }
}
