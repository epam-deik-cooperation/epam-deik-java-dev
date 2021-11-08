package com.epam.training.ticketservice;

import com.epam.training.ticketservice.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class AuthenticationCommands extends SecuredCommands {

    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;

    private static final String LOGIN_FAILED_BAD_CREDENTIALS = "Login failed due to incorrect credentials";

    @ShellMethod(value = "sign in username password", key = "sign in")
    @ShellMethodAvailability("isNotSignedIn")
    public String signIn(String userName, String password) {

        Authentication request = new UsernamePasswordAuthenticationToken(userName, password);

        try {
            Authentication result = authenticationManager.authenticate(request);
            if (!result.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"))) {
                SecurityContextHolder.getContext().setAuthentication(result);
            } else {
                throw new BadCredentialsException("");
            }
        } catch (AuthenticationException e) {
            return LOGIN_FAILED_BAD_CREDENTIALS;
        }
        return null;
    }

    @ShellMethod(value = "sign in privileged username password", key = "sign in privileged")
    @ShellMethodAvailability("isNotSignedIn")
    public String signInPrivileged(String userName, String password) {

        Authentication request = new UsernamePasswordAuthenticationToken(userName, password);

        try {
            Authentication result = authenticationManager.authenticate(request);
            if (result.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"))) {
                SecurityContextHolder.getContext().setAuthentication(result);
            } else {
                throw new BadCredentialsException("");
            }
        } catch (AuthenticationException e) {
            return LOGIN_FAILED_BAD_CREDENTIALS;
        }
        return null;
    }


    @ShellMethod(value = "sign out", key = "sign out")
    public void signOut() {
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }

    @ShellMethod(value = "sign up userName password", key = "sign up")
    public void signUp(String userName, String password) {
        accountService.createAccount(userName, password);
    }

}
