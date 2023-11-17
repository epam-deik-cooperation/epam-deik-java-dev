package com.epam.training.ticketservice.core.service.interfaces;

public interface UserServiceInterface {
    String login(String name, String password);
    String logout();
    String describeAccount();

}
