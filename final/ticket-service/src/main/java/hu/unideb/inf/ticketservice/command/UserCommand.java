package hu.unideb.inf.ticketservice.command;

import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;

public interface UserCommand {

    default boolean isLoggedIn(LoggedInUserTrackService userTrackService) {
        return !(userTrackService.getCurrentUser().equals(new DefaultUser()));
    }

}
