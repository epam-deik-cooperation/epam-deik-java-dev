package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.service.implementations.UserServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class AuthenticatorCommand {

    private final UserServiceImplementation userService;

    @ShellMethod(key = "sign out", value = "User logout")
    public String logout() {
        return userService.logout();
    }

    @ShellMethod(key = "sign in privileged", value = "User login")
    public String login(String username, String password) {
        return userService.login(username, password);
    }

    @ShellMethod(key = "describe account", value = "Get user information")
    public String print() {
        return userService.describeAccount();
    }

}