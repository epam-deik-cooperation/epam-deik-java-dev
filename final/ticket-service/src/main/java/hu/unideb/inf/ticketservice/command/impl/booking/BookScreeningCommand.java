package hu.unideb.inf.ticketservice.command.impl.booking;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.UserCommand;
import hu.unideb.inf.ticketservice.service.connection.ConnectToBookedSeatRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookScreeningCommand implements Command, UserCommand {

    private final ConnectToBookedSeatRepository bookedSeatRepository;
    private final ConnectToBookingRepository bookingRepository;

    @Autowired
    public BookScreeningCommand(ConnectToBookedSeatRepository bookedSeatRepository, ConnectToBookingRepository bookingRepository) {
        this.bookedSeatRepository = bookedSeatRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        //TODO: Implement this
        return "To be implemented";
    }
}
