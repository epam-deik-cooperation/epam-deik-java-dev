package com.epam.training.ticketservice.businesslogic.account;

import com.epam.training.ticketservice.models.user.User;

import java.util.Optional;

public interface IAccountService {
    String signInPrivileged(String username, String password);
    void signOut();
    String describeAccount();
    Optional<User> getCurrentUser();
}
