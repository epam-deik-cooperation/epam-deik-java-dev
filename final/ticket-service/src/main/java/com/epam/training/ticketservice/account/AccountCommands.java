package com.epam.training.ticketservice.account;

import com.epam.training.ticketservice.booking.Booking;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AccountCommands {

    private final AccountService accountService;


    @ShellMethod(value = "describe account", key = "describe account")
    public String describeAccount() throws NotFoundException {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {

            String user = SecurityContextHolder.getContext().getAuthentication().getName();

            List<Booking> bookings = accountService.findByUserName(user).getBookings();

            StringBuilder sb = new StringBuilder();
            sb.append("Signed in with");

            if (isAdminLoggedIn()) {

                sb.append(String.format(" privileged account '%s'", user));

                return sb.toString();
            } else if (!isAdminLoggedIn()) {
                sb.append(String.format(" account '%s'\n", user));

                if (!bookings.isEmpty()) {
                    sb.append("Your previous bookings are\n");
                    bookings.forEach(x -> sb.append(x.toString())
                            .append("\n"));
                    sb.setLength(sb.length() - 1);
                } else {
                    sb.append("You have not booked any tickets yet");
                }
                return sb.toString();

            }
        }
        return "You are not signed in";
    }

    private boolean isAdminLoggedIn() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"));
    }

}