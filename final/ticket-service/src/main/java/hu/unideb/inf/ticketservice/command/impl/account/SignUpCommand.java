package hu.unideb.inf.ticketservice.command.impl.account;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.model.user.User;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.connection.ConnectToUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignUpCommand implements Command {

    private static final String DEFAULT_USERNAME = "default";

    private final ConnectToUserRepository userRepository;
    private final AdminCredentialsProvider adminCredentialsProvider;

    @Autowired
    public SignUpCommand(ConnectToUserRepository userRepository,
                         AdminCredentialsProvider adminCredentialsProvider) {
        this.userRepository = userRepository;
        this.adminCredentialsProvider = adminCredentialsProvider;
    }

    @Override
    public String execute(List<String> parameters) {
        String username = parameters.get(0);
        List<User> users = userRepository.getUserList();
        if (nonMatchingUsername(username, users)) {
            if (!username.equals(DEFAULT_USERNAME)) {
                if(!username.equals(adminCredentialsProvider.getUsername())) {
                    userRepository.saveUser(new User(username, parameters.get(1), false));
                    return "Successfully signed up";
                } else {
                    return "The username cannot be " + username;
                }
            } else {
                return "The username cannot be " + DEFAULT_USERNAME;
            }
        } else {
            return "There is already a username with " + username;
        }
    }

    private boolean nonMatchingUsername(String username, List<User> users) {
        return users.stream().noneMatch(u -> u.getUsername().equals(username));
    }
}
