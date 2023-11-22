package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.model.Seat;
import com.epam.training.ticketservice.core.model.User;
import com.epam.training.ticketservice.core.service.interfaces.BookingServiceInterface;
import com.epam.training.ticketservice.core.service.interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ShellComponent
@RequiredArgsConstructor
public class BookingCommand {
    private final BookingServiceInterface bookingServiceInterface;
    private final UserServiceInterface userServiceInterface;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Transactional
    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "book", value = "Creates a booking")
    public String createBooking(String movieName, String roomName, String screeningDate, String seats) {
        try {
            return bookingServiceInterface.book(movieName, roomName,
                    LocalDateTime.parse(screeningDate, formatter), seats);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Availability isAvailable() {
        if (userServiceInterface.describeAccount().isPresent()
                && userServiceInterface.describeAccount().get().role().equals(User.Role.USER)) {
            return Availability.available();
        } else {
            return Availability.unavailable("You are not authorized");
        }
    }
}
