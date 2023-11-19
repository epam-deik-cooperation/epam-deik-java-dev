package com.epam.training.ticketservice.core.service.interfaces;

import com.epam.training.ticketservice.core.dto.UserDto;
import com.epam.training.ticketservice.core.model.User;

import java.util.Optional;

public interface UserServiceInterface {
    Optional<UserDto> login(String name, String password);

    boolean isValidCredentials(User user, String password);

    Optional<UserDto> logout();

    Optional<UserDto> describeAccount();

}
