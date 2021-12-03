package hu.unideb.inf.ticketservice.service;

import hu.unideb.inf.ticketservice.model.user.User;

public interface LoggedInUserTrackService {

    User getCurrentUser();

    void updateCurrentUser(User user);

}
