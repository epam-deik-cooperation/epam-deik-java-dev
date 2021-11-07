package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.SecuredCommands;
import com.epam.training.ticketservice.account.Account;
import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.exception.ConflictException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class BookingCommands extends SecuredCommands {

    private final AccountService accountService;
    private final BookingService bookingService;

    @ShellMethod(value = "book movieTitle roomName date seats", key = "book")
    @ShellMethodAvailability("isAccountUser")
    public void book(String movieTitle, String roomName, String date, String seats) {

        try {
            Account account = accountService.findByUserName(SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName());

            Booking booking = bookingService.mapToBooking(movieTitle, roomName, date, seats, account);

            bookingService.createBooking(booking);

        } catch (NotFoundException | ConflictException e) {
            System.out.println(e.getMessage());
        }
    }

    @ShellMethod(value = "show price for movieTitle roomName date seats", key = "show price for")
    public String showPriceFor(String movieTitle, String roomName, String date, String seats) throws ConflictException {

        try {
            return bookingService.showPriceForBooking(movieTitle, roomName, date, seats);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
    }

}
