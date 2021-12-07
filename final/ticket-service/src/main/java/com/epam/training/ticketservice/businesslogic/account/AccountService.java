package com.epam.training.ticketservice.businesslogic.account;

import com.epam.training.ticketservice.models.user.User;
import com.epam.training.ticketservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    private User currentUser = null;
    private UserRepository userRepository;

    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    //Seeding the userRepository with the admin account
    public void init() {
        userRepository.save(new User("admin", "admin", User.Role.ADMIN));
    }

    @Override
    public String signInPrivileged(String username, String password) {
        var optional = getByName(username);
        if (optional.isPresent()) {
            String userPassword = optional.get().getPassword();
            if (userPassword.equals(password)) {
                currentUser = optional.get();
                return null;
            }
        }
        return "Login failed due to incorrect credentials";
    }

    @Override
    public void signOut() {
        currentUser = null;
    }

    @Override
    public String describeAccount() {
        if (currentUser == null) {
            return "You are not signed in";
        }
        if (currentUser.getRole().equals(User.Role.ADMIN)) {
            return "Signed in with privileged account '" +
                    currentUser.getUsername() +
                    "'";
        }
        return "USER role handling is not yet implemented";
    }

    @Override
    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }
    public Optional<User> getByName(String username) {
        return userRepository.findByUsername(username);
    }
}

