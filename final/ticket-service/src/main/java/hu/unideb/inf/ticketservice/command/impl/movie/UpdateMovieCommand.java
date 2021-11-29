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
public class UpdateMovieCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackService userService;
    private final ConnectToMovieRepository movieRepository;

    @Autowired
    public UpdateMovieCommand(LoggedInUserTrackService userService, ConnectToMovieRepository movieRepository) {
        this.userService = userService;
        this.movieRepository = movieRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userService)) {
            String movieName = parameters.get(0);
            List<Movie> movieList = movieRepository.listMovies();
            Movie toBeUpdated = findMovieByName(movieName, movieList);
            if (toBeUpdated == null) {
                return "No such movie like " + movieName;
            } else {
                Movie movie = new Movie(movieName, parameters.get(1), Integer.valueOf(parameters.get(2)));
                movieRepository.updateMovie(movie.getName(), movie);
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
