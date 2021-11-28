package hu.unideb.inf.ticketservice.command.impl.movie;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateMovieCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackService userService;
    private final ConnectToRepositoriesService repositoriesService;

    @Autowired
    public CreateMovieCommand(LoggedInUserTrackService userService, ConnectToRepositoriesService repositoriesService) {
        this.userService = userService;
        this.repositoriesService = repositoriesService;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userService)) {
            Movie movie = new Movie(parameters.get(0), parameters.get(1), Integer.valueOf(parameters.get(2)));
            repositoriesService.createMovie(movie);
            return "Alright";
        } else {
            return "Unauthorized action!";
        }
    }

}
