package com.epam.training.ticketservice.core.service.implementations;

import com.epam.training.ticketservice.core.repository.UserRepository;
import com.epam.training.ticketservice.core.model.User;
import com.epam.training.ticketservice.core.service.interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserServiceInterface {
    private final UserRepository userRepository;
    private User loggedInUser = null;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public String login(String name, String password) {
        Optional<User> user = userRepository.findByNameAndPassword(name, password);
        if (user.isEmpty()) {
            return "Login failed due to incorrect credentials";
        } else {
            if (loggedInUser == null) {
                loggedInUser = new User(user.get().getName(), user.get().getPassword(), user.get().getRole());
                return "Successfully signed in";
            } else {
                return "User already signed in";
            }
        }
    }

    @Override
    public String logout() {
        if (loggedInUser == null) {
            return "You are not signed in";
        } else {
            loggedInUser = null;
            return "Successfully signed out";
        }
    }

    @Override
    public String describeAccount() {
        if (loggedInUser == null) {
            return "You are not signed in";
        } else {
            return "Signed in with privileged account '" + loggedInUser.getName() + "'";
        }
    }
}
