package hu.unideb.inf.ticketservice.service;

import hu.unideb.inf.ticketservice.model.user.AbstractUser;

public interface LoggedInUserTrackService {

    AbstractUser getCurrentUser();

    void updateCurrentUser(AbstractUser user);

}
