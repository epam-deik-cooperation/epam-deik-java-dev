package hu.unideb.inf.ticketservice.service.impl;

import hu.unideb.inf.ticketservice.model.user.UserInterface;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;

public class LoggedInUserTrackImpl implements LoggedInUserTrackService {

    private UserInterface currentUser;

    public LoggedInUserTrackImpl(UserInterface user) {
        currentUser = user;
    }

    @Override
    public UserInterface getCurrentUser() {
        return currentUser;
    }

    @Override
    public void updateCurrentUser(UserInterface user) {
        currentUser = user;
    }
}
