package hu.unideb.inf.ticketservice.command.handler;

import hu.unideb.inf.ticketservice.command.impl.account.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class UserCommandHandler {

    private final DescribeAccountCommand describeAccountCommand;
    private final SignInPrivilegedCommand signInPrivilegedCommand;
    private final SignUpCommand signUpCommand;
    private final SignOutCommand signOutCommand;
    private final SignInCommand signInCommand;

    @Autowired
    public UserCommandHandler(DescribeAccountCommand describeAccountCommand,
                              SignInPrivilegedCommand signInPrivilegedCommand, SignUpCommand signUpCommand,
                              SignOutCommand signOutCommand, SignInCommand signInCommand) {
        this.describeAccountCommand = describeAccountCommand;
        this.signInPrivilegedCommand = signInPrivilegedCommand;
        this.signUpCommand = signUpCommand;
        this.signOutCommand = signOutCommand;
        this.signInCommand = signInCommand;
    }

    @ShellMethod(value = "Signs in as admin", key = "sign in privileged")
    public String signInPrivileged(String username, String password) {
        return signInPrivilegedCommand.execute(List.of(username,password));
    }

    @ShellMethod(value = "Signs out", key = "sign out")
    public String signOut() {
        return signOutCommand.execute(null);
    }

    @ShellMethod(value = "Describes the current account", key = "describe account")
    public String describeAccount() {
        return describeAccountCommand.execute(null);
    }

    @ShellMethod(value = "Signs up a new user", key = "sign up")
    public String signUp(String username, String password) {
        return signUpCommand.execute(List.of(username,password));
    }

    @ShellMethod(value = "Signs into a user", key = "sign in")
    public String signIn(String username, String password) {
        return signInCommand.execute(List.of(username,password));
    }


}
