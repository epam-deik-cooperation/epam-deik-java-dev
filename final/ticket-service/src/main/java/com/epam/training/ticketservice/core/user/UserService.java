package com.epam.training.ticketservice.core.user;


import java.util.Optional;

public interface UserService {

    Optional<UserDto> login(String username, String password);

    Optional<UserDto> adminLogin(String username, String password);

    Optional<UserDto> logout();

    Optional<UserDto> describe();

    void registerUser(String username, String password);
}
