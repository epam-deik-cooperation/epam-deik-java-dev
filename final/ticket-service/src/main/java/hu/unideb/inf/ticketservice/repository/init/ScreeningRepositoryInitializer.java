package hu.unideb.inf.ticketservice.repository.init;

import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.service.connection.ConnectToMovieRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRoomRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

//Uncomment the line below to initialize repository
//@Repository
public class ScreeningRepositoryInitializer {

    private final ConnectToScreeningRepository screeningRepository;
    private final ConnectToRoomRepository roomRepository;
    private final ConnectToMovieRepository movieRepository;

    @Autowired
    public ScreeningRepositoryInitializer(ConnectToScreeningRepository screeningRepository,
                                          ConnectToRoomRepository roomRepository,
                                          ConnectToMovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    private void saveScreenings() {
        List<String> dates = List.of(
                "2021-12-10 15:00",
                "2021-12-10 17:00",
                "2021-12-10 20:00"
        );
        List<Movie> movieList = movieRepository.listMovies();
        List<Room> roomList = roomRepository.listRooms();
        List<Screening> initialScreenings = List.of(
                new Screening(movieList.get(0), roomList.get(0), dates.get(0)),
                new Screening(movieList.get(1), roomList.get(1), dates.get(1)),
                new Screening(movieList.get(2), roomList.get(2), dates.get(2))
        );
        initialScreenings.forEach(screeningRepository::createScreening);
    }
}
