package hu.unideb.inf.ticketservice.command.impl.account;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignInPrivilegedCommand implements Command {

    private final LoggedInUserTrackService service;
    private final AdminCredentialsProvider credentialsProvider;

    @Autowired
    public SignInPrivilegedCommand(LoggedInUserTrackService service,AdminCredentialsProvider credentialsProvider) {
        this.service = service;
        this.credentialsProvider = credentialsProvider;
    }

    @Override
    public String execute(List<String> parameters) {
        String username = credentialsProvider.getUsername();
        String password = credentialsProvider.getPassword();
        if (username.equals(parameters.get(0)) && password.equals(parameters.get(1))) {
            service.updateCurrentUser(new Administrator(credentialsProvider));
            return "Signed in";
        } else {
            return "Login failed due to incorrect credentials";
        }
    }
}
