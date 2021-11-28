package hu.unideb.inf.ticketservice.command.impl.movie;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.util.List;

@Component
public class ListMoviesCommand implements Command {

    private final ConnectToRepositoriesService repositoriesService;

    @Autowired
    public ListMoviesCommand(ConnectToRepositoriesService repositoriesService) {
        this.repositoriesService = repositoriesService;
    }

    @Override
    public String execute(@Null List<String> parameters) {
        List<Movie> movies = repositoriesService.listMovies();
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0;i < movies.size();i++) {
                if (i + 1 == movies.size()) {
                    result.append(movies.get(i));
                } else {
                    result.append(movies.get(i));
                    result.append("\n");
                }
            }
            return result.toString();
        }
    }
}
