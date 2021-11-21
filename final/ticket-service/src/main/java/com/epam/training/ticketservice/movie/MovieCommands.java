package com.epam.training.ticketservice.movie;


import com.epam.training.ticketservice.SecuredCommands;
import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommands extends SecuredCommands {

    private final MovieService movieService;

    @ShellMethod(value = "create movie title genre length", key = "create movie")
    @ShellMethodAvailability("isAccountAdmin")
    public String createMovie(String title, String genre, int length) {

        try {
            Movie movie = movieService.mapToMovie(title, genre, length);
            movieService.createMovie(movie);

        } catch (AlreadyExistsException e) {
            return e.getMessage();
        }

        return String.format("Successfully created movie '%s'", title);
    }

    @ShellMethod(value = "update movie title genre length", key = "update movie")
    @ShellMethodAvailability("isAccountAdmin")
    public String updateMovie(String title, String genre, int length) {

        try {
            Movie movie = movieService.mapToMovie(title, genre, length);
            movieService.updateMovie(movie);

        } catch (NotFoundException e) {
            return e.getMessage();
        }

        return String.format("Successfully updated movie '%s'", title);
    }


    @ShellMethod(value = "delete movie title", key = "delete movie")
    @ShellMethodAvailability("isAccountAdmin")
    public String deleteMovie(String title) {
        try {
            movieService.deleteMovie(title);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
        return String.format("Successfully deleted movie '%s'", title);
    }

    @ShellMethod(value = "list movies", key = "list movies")
    public String listMovies() {

        StringBuilder sb = new StringBuilder();

        if (!movieService.getAllMovies().isEmpty()) {
            movieService.getAllMovies().forEach(x -> sb.append(x).append("\n"));
            sb.setLength(sb.length() - 1);
            return sb.toString();
        } else {
            return "There are no movies at the moment";
        }
    }


}
