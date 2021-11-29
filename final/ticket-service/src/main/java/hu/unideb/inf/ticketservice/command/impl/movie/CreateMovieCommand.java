package hu.unideb.inf.ticketservice.command.impl.movie;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateMovieCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackService userService;
    private final ConnectToMovieRepository movieRepository;

    @Autowired
    public CreateMovieCommand(LoggedInUserTrackService userService, ConnectToMovieRepository movieRepository) {
        this.userService = userService;
        this.movieRepository = movieRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userService)) {
            Movie movie = new Movie(parameters.get(0), parameters.get(1), Integer.valueOf(parameters.get(2)));
            movieRepository.createMovie(movie);
            return "Alright";
        } else {
            return "Unauthorized action!";
        }
    }

}
