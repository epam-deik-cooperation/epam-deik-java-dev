package hu.unideb.inf.ticketservice.command.impl.account;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.model.user.User;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignInCommand implements Command {

    private final ConnectToUserRepository userRepository;
    private final LoggedInUserTrackService userTrackService;

    @Autowired
    public SignInCommand(ConnectToUserRepository userRepository, LoggedInUserTrackService userTrackService) {
        this.userRepository = userRepository;
        this.userTrackService = userTrackService;
    }


    @Override
    public String execute(List<String> parameters) {
        List<User> users = userRepository.getUserList();
        User searchUSer = new User(parameters.get(0), parameters.get(1), false);
        User actual = findUser(searchUSer,users);
        if (actual != null) {
            userTrackService.updateCurrentUser(actual);
            return "Successfully signed in";
        } else {
            return "Login failed due to incorrect credentials";
        }
    }

    private User findUser(User user, List<User> users) {
        return users.stream()
                .filter(u -> u.equals(user))
                .findFirst()
                .orElse(null);
    }
}
