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
public class UpdateMovieCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackService userService;
    private final ConnectToRepositoriesService repositoriesService;

    @Autowired
    public UpdateMovieCommand(LoggedInUserTrackService userService, ConnectToRepositoriesService repositoriesService) {
        this.userService = userService;
        this.repositoriesService = repositoriesService;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userService)) {
            String movieName = parameters.get(0);
            List<Movie> movieList = repositoriesService.listMovies();
            Movie toBeUpdated = findMovieByName(movieName, movieList);
            if (toBeUpdated == null) {
                return "No such movie like " + movieName;
            } else {
                Movie movie = new Movie(movieName, parameters.get(1), Integer.valueOf(parameters.get(2)));
                repositoriesService.updateMovie(movie.getName(), movie);
                return "Alright";
            }
        } else {
            return "Unauthorized action!";
        }
    }

    private Movie findMovieByName(String name, List<Movie> movieList) {
        return movieList.stream()
                .filter(m -> m.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
