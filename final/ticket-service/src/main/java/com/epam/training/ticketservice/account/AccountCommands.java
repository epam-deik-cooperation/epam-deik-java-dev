package com.epam.training.ticketservice.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class AccountCommands {

    private final AccountService accountService;


    @ShellMethod(value = "describe account", key = "describe account")
    public String describeAccount() {

        if(SecurityContextHolder.getContext().getAuthentication() != null) {
          String user = SecurityContextHolder.getContext().getAuthentication().getName();
            return String.format("Signed in with privileged account '%s'", user);
        } else {
            return "You are not signed in";
        }
    }



}
