package hu.unideb.inf.ticketservice.service;

import hu.unideb.inf.ticketservice.model.user.UserInterface;

public interface LoggedInUserTrackService {

    UserInterface getCurrentUser();

    void updateCurrentUser(UserInterface user);

}
