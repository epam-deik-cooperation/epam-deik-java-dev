package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;

import java.util.Optional;

@RequiredArgsConstructor
public class Authentication {
    public static Availability admin(Optional<User> currentUser) {
        if (currentUser.isPresent()) {
            if (currentUser.get().getRole().equals(User.Role.ADMIN)) {
                return Availability.available();
            }
        }
        return Availability.unavailable("You must be an admin for this command.");
    }
}
