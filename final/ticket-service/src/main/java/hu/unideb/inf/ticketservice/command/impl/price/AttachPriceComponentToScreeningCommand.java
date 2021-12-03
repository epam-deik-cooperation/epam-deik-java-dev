package hu.unideb.inf.ticketservice.command.impl.price;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToComponentRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToMovieRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRoomRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttachPriceComponentToScreeningCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackService userTrackService;
    private final ConnectToComponentRepository componentRepository;
    private final ConnectToScreeningRepository screeningRepository;
    private final ConnectToRoomRepository roomRepository;
    private final ConnectToMovieRepository movieRepository;

    @Autowired
    public AttachPriceComponentToScreeningCommand(LoggedInUserTrackService userTrackService,
                                                  ConnectToComponentRepository componentRepository,
                                                  ConnectToScreeningRepository screeningRepository,
                                                  ConnectToRoomRepository roomRepository,
                                                  ConnectToMovieRepository movieRepository) {
        this.userTrackService = userTrackService;
        this.componentRepository = componentRepository;
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userTrackService)) {
            String componentName = parameters.get(0);
            List<PriceComponent> priceComponentList = componentRepository.getComponents();
            PriceComponent actualComponent = findComponentByName(componentName, priceComponentList);
            if (actualComponent != null) {
                String movieName = parameters.get(1);
                List<Movie> movieList = movieRepository.listMovies();
                Movie actualMovie = findMovieByName(movieName, movieList);
                if (actualMovie != null) {
                    String roomName = parameters.get(2);
                    List<Room> roomList = roomRepository.listRooms();
                    Room actualRoom = findRoomByName(roomName, roomList);
                    if (actualRoom != null) {
                        String date = parameters.get(3);
                        List<Screening> screeningList = screeningRepository.listScreenings();
                        Screening actualScreening = findScreening(actualMovie, actualRoom, date, screeningList);
                        if (actualScreening != null) {
                            screeningRepository.updateComponent(actualScreening,actualComponent);
                            actualScreening.setComponent(actualComponent);
                            return "Alright";
                        } else {
                            return "There is no screening with movie " + movieName + " in room " + roomName
                                    + " at " + date;
                        }
                    } else {
                        return "There is no room with name " + roomName;
                    }
                } else {
                    return "There is no movie with name " + movieName;
                }
            } else {
                return "There is no component with name " + componentName;
            }
        } else {
            return "Unauthorized action!";
        }
    }

    private Room findRoomByName(String roomName, List<Room> roomList) {
        return roomList.stream()
                .filter(c -> c.getName().equals(roomName))
                .findFirst()
                .orElse(null);
    }

    private Movie findMovieByName(String movieName, List<Movie> movieList) {
        return movieList.stream()
                .filter(c -> c.getName().equals(movieName))
                .findFirst()
                .orElse(null);
    }

    private Screening findScreening(Movie movie, Room room, String date,List<Screening> screeningList) {
        return screeningList.stream()
                .filter(c -> c.getMovie().equals(movie) && c.getRoom().equals(room)
                        && c.getScreeningDate().equals(date))
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
