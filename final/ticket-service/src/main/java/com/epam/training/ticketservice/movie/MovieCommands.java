package com.epam.training.ticketservice.movie;


import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommands {

    private final MovieService movieService;

    @ShellMethod(value = "format: create movie title genre length", key = "create movie")
    public String create(String title, String genre, int length) {

        movieService.createMovie(Movie.builder()
                .title(title)
                .genre(genre)
                .length(length)
                .build());

        return String.format("Successfully created movie '%s'", title);
    }

    @ShellMethod(value = "format: update movie title genre length", key = "update movie")
    public String update(String title, String genre, int length) {
        /*
            TODO:
                - 'update movie' command implementation
        */
        return "";
    }

    public String delete(String title, String genre, int length) {
        /*
            TODO:
                - 'delete movie' command implementation
        */
        return "";
    }

    public String list() {
        /*
            TODO:
                - 'list movies' command implementation
        */

        return "";
    }


}
