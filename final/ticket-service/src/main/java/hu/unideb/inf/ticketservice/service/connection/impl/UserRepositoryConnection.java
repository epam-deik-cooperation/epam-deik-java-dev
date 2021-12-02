package hu.unideb.inf.ticketservice.service.connection.impl;

import hu.unideb.inf.ticketservice.model.user.User;
import hu.unideb.inf.ticketservice.repository.UserRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToUserRepository;

import java.util.List;

public class UserRepositoryConnection implements ConnectToUserRepository {

    private final UserRepository userRepository;

    public UserRepositoryConnection(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }
}
