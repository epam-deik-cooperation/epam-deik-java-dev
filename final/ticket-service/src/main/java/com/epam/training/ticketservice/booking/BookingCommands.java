package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.account.Account;
import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.booking.price.PriceCalculator;
import com.epam.training.ticketservice.exception.ConflictException;
import com.epam.training.ticketservice.room.Seat;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.SecuredCommands;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookingCommands extends SecuredCommands {

    private final AccountService accountService;
    private final ScreeningService screeningService;
    private final PriceCalculator priceCalculator;
    private final BookingService bookingService;

    @ShellMethod(value = "book movieTitle roomName date seats", key = "book")
    @ShellMethodAvailability("isAccountUser")
    public void book(String movieTitle, String roomName, String date, String seats) {

        Account account = accountService.findByUserName(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());

        try {
            Screening screening = screeningService.getScreeningByProperties(movieTitle, roomName, date);

            List<Seat> seatsToBook = new ArrayList<>();

            Arrays.stream(seats.split(" ")).forEach(x -> {
                List<String> seatSpot = Arrays.asList(x.split(","));
                seatsToBook.add(new Seat(Integer.parseInt(seatSpot.get(0)), Integer.parseInt(seatSpot.get(1))));
            });


            Booking booking = Booking.builder()
                    .account(account)
                    .screening(screening)
                    .seats(seatsToBook)
                    .price(priceCalculator.calculate(screening, seatsToBook.size()))
                    .build();

            bookingService.createBooking(booking);
        } catch (NotFoundException | ConflictException e) {
            System.out.println(e.getMessage());
        }
    }


}
