package hu.unideb.inf.ticketservice.command.handler;

import hu.unideb.inf.ticketservice.command.impl.movie.CreateMovieCommand;
import hu.unideb.inf.ticketservice.command.impl.movie.DeleteMovieCommand;
import hu.unideb.inf.ticketservice.command.impl.movie.ListMoviesCommand;
import hu.unideb.inf.ticketservice.command.impl.movie.UpdateMovieCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class MovieCommandHandler {

    private final CreateMovieCommand createMovieCommand;
    private final DeleteMovieCommand deleteMovieCommand;
    private final ListMoviesCommand listMoviesCommand;
    private final UpdateMovieCommand updateMovieCommand;

    @Autowired
    public MovieCommandHandler(CreateMovieCommand createMovieCommand, DeleteMovieCommand deleteMovieCommand,
                               ListMoviesCommand listMoviesCommand, UpdateMovieCommand updateMovieCommand) {
        this.createMovieCommand = createMovieCommand;
        this.deleteMovieCommand = deleteMovieCommand;
        this.listMoviesCommand = listMoviesCommand;
        this.updateMovieCommand = updateMovieCommand;
    }

    @ShellMethod(value = "Creates a movie", key = "create movie")
    public String createMovie(String name, String genre, String length) {
        return createMovieCommand.execute(List.of(name,genre,length));
    }

    @ShellMethod(value = "Deletes a movie", key = "delete movie")
    public String deleteMovie(String name) {
        return deleteMovieCommand.execute(List.of(name));
    }

    @ShellMethod(value = "Updates a movie", key = "update movie")
    public String updateMovie(String name, String genre, String length) {
        return updateMovieCommand.execute(List.of(name,genre,length));
    }

    @ShellMethod(value = "Lists movies", key = "list movies")
    public String listRooms() {
        return listMoviesCommand.execute(null);
    }

}
