package com.epam.training.ticketservice.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private UserDto userDto = null;

    @Override
    public Optional<UserDto> login(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPasswordAndRole(username, password, User.Role.USER);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        UserDto userDto1 = new UserDto(user.get().getUsername(), user.get().getRole());
        userDto = userDto1;
        return Optional.of(userDto1);
    }

    @Override
    public Optional<UserDto> adminLogin(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPasswordAndRole(username, password, User.Role.ADMIN);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        UserDto userDto1 = new UserDto(user.get().getUsername(), user.get().getRole());
        userDto = userDto1;
        return Optional.of(userDto1);
    }

    @Override
    public Optional<UserDto> logout() {
        Optional<UserDto> user = Optional.ofNullable(userDto);
        userDto = null;
        return user;
    }

    @Override
    public Optional<UserDto> describe() {
        return Optional.ofNullable(userDto);
    }

    @Override
    public void registerUser(String username, String password) {
        User user = new User(username, password, User.Role.USER);
        userRepository.save(user);
    }
}
