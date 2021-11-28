package hu.unideb.inf.ticketservice.command.handler;

import hu.unideb.inf.ticketservice.command.impl.account.DescribeAccountCommand;
import hu.unideb.inf.ticketservice.command.impl.account.SignInPrivilegedCommand;
import hu.unideb.inf.ticketservice.command.impl.account.SignOutCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class UserCommandHandler {

    private final DescribeAccountCommand describeAccountCommand;
    private final SignInPrivilegedCommand signInPrivilegedCommand;
    private final SignOutCommand signOutCommand;

    @Autowired
    public UserCommandHandler(DescribeAccountCommand describeAccountCommand,
                              SignInPrivilegedCommand signInPrivilegedCommand, SignOutCommand signOutCommand) {
        this.describeAccountCommand = describeAccountCommand;
        this.signInPrivilegedCommand = signInPrivilegedCommand;
        this.signOutCommand = signOutCommand;
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


}
