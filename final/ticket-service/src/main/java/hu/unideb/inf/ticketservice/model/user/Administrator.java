package hu.unideb.inf.ticketservice.model.user;

import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;

public class Administrator implements UserInterface {

    private String username;
    private Integer passwordHash;
    private boolean isPrivileged;

    public Administrator(AdminCredentialsProvider credentialsProvider) {
        username = credentialsProvider.getUsername();
        passwordHash = credentialsProvider.getPassword().hashCode();
        isPrivileged = true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isPrivileged() {
        return isPrivileged;
    }

    @Override
    public Integer getPasswordHash() {
        return passwordHash;
    }
}
