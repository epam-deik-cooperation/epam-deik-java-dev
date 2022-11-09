package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class UserCommand {

    private UserService userService;

    @ShellMethod(key = "sign up", value = "Registers a user")
    public String register(String username, String password) {
        userService.registerUser(username, password);
        return "User successfully registered";
    }

    @ShellMethod(key = "sign in", value = "Signs in a user")
    public String login(String username, String password) {
        Optional<UserDto> user = userService.login(username, password);
        if (user.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }

        return "User " + user.get().getUsername() + " logged in";
    }

    @ShellMethod(key = "sign in privileged", value = "Signs in an admin")
    public String adminLogin(String username, String password) {
        Optional<UserDto> user = userService.adminLogin(username, password);
        if (user.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }

        return "User " + user.get().getUsername() + " logged in";
    }

    @ShellMethod(key = "sign out", value = "Signs out a user")
    public String logout() {
        Optional<UserDto> user = userService.logout();
        if (user.isEmpty()) {
            return "Nobody was logged in";
        }
        return "User " + user.get().getUsername() + " successfully logged out";
    }

    @ShellMethod(key = "describe account", value = "Describes a user's account")
    public String describe() {
        Optional<UserDto> user = userService.describe();
        if (user.isEmpty()) {
            return "You are not signed in";
        }
        if (user.get().getRole().equals(User.Role.ADMIN)) {
            return "Signed in with privileged account: " + user.get().getUsername();
        } else {
            return "Signed in with account: " + user.get().getUsername();
        }
    }
}
