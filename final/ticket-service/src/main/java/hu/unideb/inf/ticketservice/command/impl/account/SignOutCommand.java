package hu.unideb.inf.ticketservice.command.impl.account;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.model.user.AbstractUser;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.util.List;

@Component
public class SignOutCommand implements Command {

    private final LoggedInUserTrackService service;
    private static final String DEFAULT_USERNAME = "default";

    @Autowired
    public SignOutCommand(LoggedInUserTrackService service) {
        this.service = service;
    }

    @Override
    public String execute(@Null List<String> parameters) {
        AbstractUser current = service.getCurrentUser();
        if (!(current.getUsername().equals(DEFAULT_USERNAME))) {
            service.updateCurrentUser(new DefaultUser());
            return "Signed out";
        }
        return "You are not signed in";
    }
}
