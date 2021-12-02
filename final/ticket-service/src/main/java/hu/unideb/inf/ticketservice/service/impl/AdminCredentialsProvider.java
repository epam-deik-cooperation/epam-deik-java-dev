package hu.unideb.inf.ticketservice.service.impl;

import org.springframework.stereotype.Service;

@Service
public class AdminCredentialsProvider {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public String getUsername() {
        return ADMIN_USERNAME;
    }

    public String getPassword() {
        return ADMIN_PASSWORD;
    }
}
