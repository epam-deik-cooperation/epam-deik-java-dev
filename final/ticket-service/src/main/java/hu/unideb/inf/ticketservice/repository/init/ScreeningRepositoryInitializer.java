package hu.unideb.inf.ticketservice.repository.init;

import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

//Uncomment the line below to initialize repository
//@Repository
public class ScreeningRepositoryInitializer {

    private final ConnectToRepositoriesService repositoriesService;

    @Autowired
    public ScreeningRepositoryInitializer(ConnectToRepositoriesService repositoriesService) {
        this.repositoriesService = repositoriesService;
    }

    @PostConstruct
    private void saveScreenings() {
        List<String> dates = List.of(
                "2021-12-10 15:00",
                "2021-12-10 17:00",
                "2021-12-10 20:00"
        );
        List<Movie> movieList = repositoriesService.listMovies();
        List<Room> roomList = repositoriesService.listRooms();
        List<Screening> initialScreenings = List.of(
                new Screening(movieList.get(0), roomList.get(0), dates.get(0)),
                new Screening(movieList.get(1), roomList.get(1), dates.get(1)),
                new Screening(movieList.get(2), roomList.get(2), dates.get(2))
        );
        initialScreenings.forEach(repositoriesService::createScreening);
    }
}
