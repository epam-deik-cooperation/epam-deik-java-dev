package com.epam.training.ticketservice.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.Availability;

public abstract class SecuredCommands {


    public Availability isAccountAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return Availability.unavailable("you are not signed in. Please sign in to be able to use this command!");
        }
        if (authentication.getAuthorities().stream().noneMatch(x -> x.getAuthority().equals("ROLE_ADMIN"))) {
            return Availability.unavailable("you are not an admin :( ");
        }
        return Availability.available();
    }

    public Availability isAccountUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return Availability.unavailable("you are not signed in. Please sign in to be able to use this command!");
        } else {
            return Availability.available();
        }
    }

    public Availability isNotSignedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return Availability.available();
        } else {
            return Availability.unavailable("You have already signed in");
        }
    }
}
