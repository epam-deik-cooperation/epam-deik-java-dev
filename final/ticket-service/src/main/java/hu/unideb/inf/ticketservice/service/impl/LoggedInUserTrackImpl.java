package hu.unideb.inf.ticketservice.service.impl;

import hu.unideb.inf.ticketservice.model.user.User;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;

public class LoggedInUserTrackImpl implements LoggedInUserTrackService {

    private User currentUser;

    public LoggedInUserTrackImpl(User user) {
        currentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void updateCurrentUser(User user) {
        currentUser = user;
    }
}
