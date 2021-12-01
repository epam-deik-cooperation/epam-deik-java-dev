package hu.unideb.inf.ticketservice.repository.init;

import hu.unideb.inf.ticketservice.model.user.User;
import hu.unideb.inf.ticketservice.service.connection.ConnectToUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

//Uncomment the line below to initialize repository
//@Repository
public class UserRepositoryInitializer {

    private final ConnectToUserRepository userRepository;

    private final List<User> userList = List.of(
            new User("pista","passwd",false),
            new User("almos","altat",false));

    @Autowired
    public UserRepositoryInitializer(ConnectToUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void saveUsers() {
        userList.forEach(userRepository::saveUser);

    }

}
