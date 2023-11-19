package com.epam.training.ticketservice.core.service.implementations;

import com.epam.training.ticketservice.core.dto.UserDto;
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
    public Optional<UserDto> login(String name, String password) {
        Optional<User> user = userRepository.findByUserName(name);
        if (user.isPresent() && isValidCredentials(user.get(), password)) {
            loggedInUser = new UserDto(user.get().getUserName(), user.get().getRole());
            return describeAccount();
        }
        return Optional.empty();
    }

    @Override
    public boolean isValidCredentials(User user, String password) {
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
}
