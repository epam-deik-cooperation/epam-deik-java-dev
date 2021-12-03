package hu.unideb.inf.ticketservice.command.impl.price;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.model.Seat;
import hu.unideb.inf.ticketservice.service.PriceService;
import hu.unideb.inf.ticketservice.service.SeatValidationService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowPriceCommand implements Command {

    private final ConnectToScreeningRepository screeningRepository;
    private final PriceService priceService;
    private final SeatValidationService seatValidationService;

    @Autowired
    public ShowPriceCommand(ConnectToScreeningRepository screeningRepository,
                            PriceService priceService,
                            SeatValidationService seatValidationService) {
        this.screeningRepository = screeningRepository;
        this.priceService = priceService;
        this.seatValidationService = seatValidationService;
    }

    @Override
    public String execute(List<String> parameters) {
        String movieName = parameters.get(0);
        String roomName = parameters.get(1);
        String date = parameters.get(2);
        List<Screening> screeningList = screeningRepository.listScreenings();
        Screening actualScreening = findScreening(screeningList, movieName, roomName, date);
        if (actualScreening != null) {
            String seats = parameters.get(3);
            List<Seat> seatList = seatValidationService.getSeats(seats,actualScreening.getRoom());
            Integer price = priceService.calculatePrice(List.of(actualScreening.getMovie().getComponent(),
                    actualScreening.getRoom().getComponent(), actualScreening.getComponent()),seatList.size());
            return "The price for this booking would be " + price + " " + priceService.getCurrency();
        } else {
            return "There is no screening with movie " + movieName + " in room " + roomName
                    + " at " + date;
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

    private Screening findScreening(List<Screening> screeningList, String movieName, String roomName, String date) {
        return screeningList.stream()
                .filter(s -> s.getScreeningDate().equals(date)
                        && s.getRoom().getName().equals(roomName)
                        && s.getMovie().getName().equals(movieName))
                .findFirst()
                .orElse(null);
    }
}
