package com.epam.training.ticketservice.account;

import com.epam.training.ticketservice.booking.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AccountCommands {

    private final AccountService accountService;

    private boolean isAdminLoggedIn() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"));
    }

    @ShellMethod(value = "describe account", key = "describe account")
    public String describeAccount() {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Booking> bookings = accountService.findByUserName(user).getBookings();

            StringBuilder sb = new StringBuilder();
            sb.append("Signed in with");

            if (isAdminLoggedIn()) {
                sb.append(" privileged");
            }
            sb.append(String.format(" account '%s' \n", user));

            if (!isAdminLoggedIn()) {
                bookings.forEach(x -> sb.append(x.toString()));
                sb.append((bookings.isEmpty() ? "You have not booked any tickets yet" : sb.toString()));
            }

            return sb.toString();

        } else {
            return "You are not signed in";
        }
    }


}
