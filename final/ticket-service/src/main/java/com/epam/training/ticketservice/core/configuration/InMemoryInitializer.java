package com.epam.training.ticketservice.core.configuration;

import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InMemoryInitializer {
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        userRepository.save(new User("admin", "admin", User.Role.ADMIN));
    }
}
