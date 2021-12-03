package hu.unideb.inf.ticketservice.command.impl.price;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToComponentRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttachPriceComponentToMovieCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackService userTrackService;
    private final ConnectToComponentRepository componentRepository;
    private final ConnectToMovieRepository movieRepository;

    @Autowired
    public AttachPriceComponentToMovieCommand(LoggedInUserTrackService userTrackService,
                                             ConnectToComponentRepository componentRepository,
                                              ConnectToMovieRepository movieRepository) {
        this.userTrackService = userTrackService;
        this.componentRepository = componentRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userTrackService)) {
            String componentName = parameters.get(0);
            List<PriceComponent> priceComponentList = componentRepository.getComponents();
            PriceComponent actualComponent = findComponentByName(componentName, priceComponentList);
            if (actualComponent != null) {
                String roomName = parameters.get(1);
                List<Movie> movieList = movieRepository.listMovies();
                Movie actualMovie = findMovieByName(roomName,movieList);
                if (actualMovie != null) {
                    movieRepository.updateComponent(actualMovie.getName(),actualComponent);
                    actualMovie.setComponent(actualComponent);
                    return "Alright";
                } else {
                    return "There is no movie with name " + roomName;
                }
            } else {
                return "There is no component with name " + componentName;
            }
        } else {
            return "Unauthorized action!";
        }
    }

    private Movie findMovieByName(String movieName, List<Movie> movieList) {
        return movieList.stream()
                .filter(c -> c.getName().equals(movieName))
                .findFirst()
                .orElse(null);
    }

    private PriceComponent findComponentByName(String componentName, List<PriceComponent> priceComponentList) {
        return priceComponentList.stream()
                .filter(c -> c.getName().equals(componentName))
                .findFirst()
                .orElse(null);
    }

}
