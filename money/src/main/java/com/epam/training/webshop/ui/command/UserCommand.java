package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class UserCommand {

    private final UserService userService;

    @ShellMethod(key = "user logout", value = "User logout")
    public String logout() {
        return userService.logout()
            .map(userDto -> userDto + " is logged out!")
            .orElse("You need to login first!");
    }

    @ShellMethod(key = "user login", value = "User login")
    public String login(String username, String password) {
        return userService.login(username, password)
            .map(userDto -> userDto + " is successfully logged in!")
            .orElse("No such username or password!");
    }

    @ShellMethod(key = "user print", value = "Get user information")
    public String print() {
        return userService.describe()
            .map(Record::toString)
            .orElse("You need to login first!");
    }

    @ShellMethod(key = "user register", value = "User registration")
    public String registerUser(String userName, String password) {
        try {
            userService.registerUser(userName, password);
            return "Registration was successful!";
        } catch (Exception e) {
            return "Registration failed!";
        }
    }
}
