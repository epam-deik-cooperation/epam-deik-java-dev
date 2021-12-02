package hu.unideb.inf.ticketservice.command.impl.account;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.model.Booking;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.model.user.User;
import hu.unideb.inf.ticketservice.model.user.UserInterface;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.util.List;

@Component
public class DescribeAccountCommand implements Command {

    private final LoggedInUserTrackService service;

    @Autowired
    public DescribeAccountCommand(LoggedInUserTrackService service) {
        this.service = service;
    }

    @Override
    public String execute(@Null List<String> parameters) {
        UserInterface current = service.getCurrentUser();
        if (current.isPrivileged()) {
            return "Signed in with privileged account '" + current.getUsername() + "'";
        } else if (current.equals(new DefaultUser())) {
            return "You are not signed in";
        } else if (userHasBooking(current)) {
            return "Signed in with account '" + current.getUsername() + "'\n"
                    + "Your previous bookings are\n"
                    + listBookings(current);
        } else {
            return "Signed in with account '" + current.getUsername() + "'\n"
                    + "You have not booked any tickets yet";
        }
    }

    private String listBookings(UserInterface current) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Booking b : ((User) current).getBookings()) {
            stringBuilder.append(b.toString());
        }
        return stringBuilder.toString();
    }

    private boolean userHasBooking(UserInterface current) {
        return !(((User) current).getBookings().isEmpty());
    }
}
