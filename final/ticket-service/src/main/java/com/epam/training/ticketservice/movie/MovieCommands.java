package com.epam.training.ticketservice.movie;


import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommands {

    private final MovieService movieService;

    @ShellMethod(value = "format: create movie title genre length", key = "create movie")
    public String create(String title, String genre, int length) {

        try {
            movieService.createMovie(Movie.builder()
                    .title(title)
                    .genre(genre)
                    .length(length)
                    .build());
        } catch (AlreadyExistsException e) {
            return e.getMessage();
        }

        return String.format("Successfully created movie '%s'", title);
    }

    @ShellMethod(value = "format: update movie title genre length", key = "update movie")
    public String update(String title, String genre, int length) {

        try {
            movieService.updateMovie(Movie.builder()
                    .title(title)
                    .genre(genre)
                    .length(length)
                    .build());
        } catch (NotFoundException e) {
            return e.getMessage();
        }

        return String.format("Successfully updated movie '%s'", title);
    }


    @ShellMethod(value = "format: delete movie title", key = "delete movie")
    public String delete(String title) {

        try {
            movieService.deleteMovie(title);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
        return String.format("Successfully deleted movie '%s", title);
    }

    @ShellMethod(value = "list movies", key = "list movies")
    public void list() {
        if (!movieService.getAllMovies().isEmpty()) {
            movieService.getAllMovies().forEach(System.out::println);
        } else {
            System.out.println("There are no movies at the moment");
        }
    }


}
