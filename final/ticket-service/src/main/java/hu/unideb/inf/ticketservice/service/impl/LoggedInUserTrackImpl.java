package hu.unideb.inf.ticketservice.service.impl;

import hu.unideb.inf.ticketservice.model.user.AbstractUser;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;

public class LoggedInUserTrackImpl implements LoggedInUserTrackService {

    private AbstractUser currentUser;

    public LoggedInUserTrackImpl(AbstractUser user) {
        currentUser = user;
    }

    @Override
    public AbstractUser getCurrentUser() {
        return currentUser;
    }

    @Override
    public void updateCurrentUser(AbstractUser user) {
        currentUser = user;
    }
}
