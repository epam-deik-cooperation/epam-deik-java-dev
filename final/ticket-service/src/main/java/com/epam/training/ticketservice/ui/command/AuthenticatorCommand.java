package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.dto.UserDto;
import com.epam.training.ticketservice.core.model.User;
import com.epam.training.ticketservice.core.service.interfaces.UserServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class AuthenticatorCommand {

    private final UserServiceInterface userServiceInterface;

    @ShellMethod(key = "sign out", value = "User logout")
    public String logout() {
        return userServiceInterface.logout()
                .map(userDto -> userDto.userName() + " successfully logged out")
                .orElse("There's no user logged in");
    }

    @ShellMethod(key = "sign in privileged", value = "User login")
    public String login(String username, String password) {
        return userServiceInterface.login(username, password)
                .map(userDto -> userDto.userName() + " successfully signed in")
                .orElse("Login failed due to incorrect credentials");
    }

    @ShellMethod(key = "describe account", value = "Get user information")
    public String print() {
        Optional<UserDto> optionalUserDto = userServiceInterface.describeAccount();
        if (optionalUserDto.isPresent()) {
            if (optionalUserDto.get().role().equals(User.Role.ADMIN)) {
                return "Signed in with privileged account '" + optionalUserDto.get().userName() + "'";
            } else {
                return "Signed in with user '" + optionalUserDto.get().userName() + "'";
            }
        } else {
            return "You are not signed in";
        }
    }

}