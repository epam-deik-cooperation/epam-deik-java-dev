package com.epam.training.ticketservice.core.service.interfaces;

import com.epam.training.ticketservice.core.dto.UserDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
import com.epam.training.ticketservice.core.model.User;

import java.util.Optional;

public interface UserServiceInterface {
    Optional<UserDto> signInPrivileged(String userName, String password);

    boolean isValidCredentialsPrivileged(User user, String password);

    boolean isValidCredentials(User user, String password);

    Optional<UserDto> logout();

    Optional<UserDto> describeAccount();

    void signUp(String userName, String password) throws AlreadyExists;

    Optional<UserDto> signIn(String userName, String password);

}
