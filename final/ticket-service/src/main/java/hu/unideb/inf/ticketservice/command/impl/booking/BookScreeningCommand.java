package hu.unideb.inf.ticketservice.command.impl.booking;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.UserCommand;
import hu.unideb.inf.ticketservice.model.Booking;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.model.Seat;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.PriceService;
import hu.unideb.inf.ticketservice.service.SeatValidationService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToBookedSeatRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToBookingRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToScreeningRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookScreeningCommand implements Command, UserCommand {

    private final ConnectToBookedSeatRepository bookedSeatRepository;
    private final ConnectToBookingRepository bookingRepository;
    private final SeatValidationService seatValidationService;
    private final ConnectToScreeningRepository screeningRepository;
    private final LoggedInUserTrackService userTrackService;
    private final ConnectToUserRepository userRepository;
    private final PriceService priceService;

    @Autowired
    public BookScreeningCommand(ConnectToBookedSeatRepository bookedSeatRepository,
                                ConnectToBookingRepository bookingRepository,
                                SeatValidationService seatValidationService,
                                ConnectToScreeningRepository screeningRepository,
                                LoggedInUserTrackService userTrackService, ConnectToUserRepository userRepository,
                                PriceService priceService) {
        this.bookedSeatRepository = bookedSeatRepository;
        this.bookingRepository = bookingRepository;
        this.seatValidationService = seatValidationService;
        this.screeningRepository = screeningRepository;
        this.userTrackService = userTrackService;
        this.userRepository = userRepository;
        this.priceService = priceService;
    }

    @Override
    public String execute(List<String> parameters) {
        if (!(userTrackService.getCurrentUser().equals(new DefaultUser()))) {
            List<Screening> screeningList = screeningRepository.listScreenings();
            String movieName = parameters.get(0);
            String roomName = parameters.get(1);
            String date = parameters.get(2);
            Screening screening = findScreening(screeningList, movieName,roomName,date);
            if (screening != null) {
                List<Seat> inputSeats = seatValidationService.getSeats(parameters.get(3), screening.getRoom());
                Seat possiblyBooked = seatValidationService.isAlreadyBookedSeat(inputSeats,screening.getRoom());
                if (possiblyBooked == null) {
                    Seat possiblyFalse = seatValidationService.isSeatValid(inputSeats, screening.getRoom());
                    if (possiblyFalse == null) {
                        List<PriceComponent> componentList = List.of(screening.getMovie().getComponent(),
                                screening.getRoom().getComponent(), screening.getComponent());
                        Integer price = priceService.calculatePrice(componentList, inputSeats.size());
                        Booking booking = new Booking(screening,price,inputSeats);
                        inputSeats.forEach(bookedSeatRepository::saveSeat);
                        userTrackService.getCurrentUser().addBooking(booking);
                        bookingRepository.saveBooking(booking);
                        userRepository.saveUser(userTrackService.getCurrentUser());
                        return "Seats booked: " + outputListOfSeats(inputSeats) + "; the price for this booking is "
                                + price + " " + priceService.getCurrency();
                    } else {
                        return "Seat (" + possiblyFalse.getRowNumber() + "," + possiblyFalse.getColumnNumber()
                                + ") does not exist in this room";
                    }
                } else {
                    return "Seat (" + possiblyBooked.getRowNumber() + "," + possiblyBooked.getColumnNumber()
                            + ") is already taken";
                }
            } else {
                return "There is no screening with movie " + movieName + " in room " + roomName + " at " + date;
            }
        } else {
            return "Sign in to book a seat";
        }
    }

    private String outputListOfSeats(List<Seat> inputSeats) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Seat seat : inputSeats) {
            stringBuilder.append(seat.toString());
            stringBuilder.append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2,stringBuilder.length());
        return stringBuilder.toString();
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
