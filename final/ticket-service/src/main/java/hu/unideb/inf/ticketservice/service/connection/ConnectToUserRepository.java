package hu.unideb.inf.ticketservice.service.connection;

import hu.unideb.inf.ticketservice.model.user.User;

import java.util.List;

public interface ConnectToUserRepository {

    void saveUser(User user);

    List<User> getUserList();

}
