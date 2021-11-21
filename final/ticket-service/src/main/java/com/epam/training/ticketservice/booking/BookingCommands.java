package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.SecuredCommands;
import com.epam.training.ticketservice.account.Account;
import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.exception.ConflictException;
import com.epam.training.ticketservice.room.Seat;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookingCommands extends SecuredCommands {

    private final AccountService accountService;
    private final BookingService bookingService;

    private String formatSeatListToString(List<Seat> seatList) {

        StringBuilder sb = new StringBuilder();

        seatList.forEach(x -> sb.append("(")
                .append(x.getRowIndex())
                .append(",")
                .append(x.getColumnIndex())
                .append("), "));

        sb.setLength(sb.length() - 2);

        return sb.toString();
    }

    @ShellMethod(value = "book movieTitle roomName date seats", key = "book")
    @ShellMethodAvailability("isAccountUser")
    public String book(String movieTitle, String roomName, String date, String seats) {

        try {
            Account account = accountService.findByUserName(SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName());

            Booking booking = bookingService.mapToBooking(movieTitle, roomName, date, seats, account);

            bookingService.createBooking(booking);

            return String.format("Seats booked: %s; the price for this booking is %d HUF",
                    formatSeatListToString(booking.getSeats()), booking.getPrice());

        } catch (NotFoundException | ConflictException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "show price for movieTitle roomName date seats", key = "show price for")
    public String showPriceFor(String movieTitle, String roomName, String date, String seats)
            throws ConflictException {

        try {
            Booking booking = bookingService.mapToBooking(movieTitle, roomName, date, seats);

            return bookingService.showPriceForBooking(booking);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
    }

}
