package hu.unideb.inf.ticketservice.command;

import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;

public interface PrivilegedCommand {

    default boolean isAuthorized(LoggedInUserTrackService service) {
        return service.getCurrentUser().isPrivileged();
    }
}
