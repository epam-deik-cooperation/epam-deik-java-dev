package com.epam.training.ticketservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.util.StringUtils;

@ShellComponent
@RequiredArgsConstructor
public class AuthenticationCommands extends SecuredCommands{

    private final AuthenticationManager authenticationManager;


    @ShellMethod(value = "format: sign in username password", key = "sign in")
    @ShellMethodAvailability("isNotSignedIn")
    public void signIn(String userName, String password) {

        Authentication request = new UsernamePasswordAuthenticationToken(userName, password);

        try {
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
               } catch (AuthenticationException e) {
            System.out.println("Login failed due to incorrect credentials");
        }
    }

    @ShellMethod(value = "sign out", key = "sign out")
    public void signOut() {
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }

}
