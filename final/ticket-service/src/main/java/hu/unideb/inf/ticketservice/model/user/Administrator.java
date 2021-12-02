package hu.unideb.inf.ticketservice.model.user;

import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Administrator that = (Administrator) o;
        return isPrivileged == that.isPrivileged && Objects.equals(username, that.username)
                && Objects.equals(passwordHash, that.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, passwordHash, isPrivileged);
    }
}
