package com.epam.training.ticketservice.core.service.implementations;

import com.epam.training.ticketservice.core.dto.UserDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
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
    private UserDto loggedInUser = null;

    @Override
    public Optional<UserDto> signInPrivileged(String userName, String password) {
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent() && isValidCredentialsPrivileged(user.get(), password)) {
            loggedInUser = new UserDto(user.get().getUserName(), user.get().getRole());
            return describeAccount();
        }
        return Optional.empty();
    }

    public boolean isValidCredentialsPrivileged(User user, String password) {
        return user.getPassword().equals(password) && user.getRole().equals(User.Role.ADMIN);
    }

    @Override
    public Optional<UserDto> logout() {
        Optional<UserDto> user = describeAccount();
        loggedInUser = null;
        return user;
    }

    @Override
    public Optional<UserDto> describeAccount() {
        return Optional.ofNullable(loggedInUser);
    }

    @Override
    public void signUp(String userName, String password) throws AlreadyExists {
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            throw new AlreadyExists("This user already exists");
        } else {
            User signUpUser = new User(userName, password, User.Role.USER);
            userRepository.save(signUpUser);
        }
    }

    @Override
    public Optional<UserDto> signIn(String userName, String password) {
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent() && isValidCredentials(user.get(), password)) {
            loggedInUser = new UserDto(user.get().getUserName(), user.get().getRole());
            return describeAccount();
        }
        return Optional.empty();
    }

    public boolean isValidCredentials(User user, String password) {
        return user.getPassword().equals(password) && user.getRole().equals(User.Role.USER);
    }
}
