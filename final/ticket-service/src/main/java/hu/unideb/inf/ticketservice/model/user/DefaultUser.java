package hu.unideb.inf.ticketservice.model.user;

import java.util.Objects;

public class DefaultUser implements UserInterface {

    private static final String USERNAME = "default";
    private static final String PASSWORD = "default";

    public DefaultUser() {
    }

    @Override
    public String getUsername() {
        return USERNAME;
    }

    @Override
    public boolean isPrivileged() {
        return false;
    }

    @Override
    public Integer getPasswordHash() {
        return PASSWORD.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(),getPasswordHash());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DefaultUser that = (DefaultUser) obj;
        return isPrivileged() == that.isPrivileged() && Objects.equals(getUsername(), that.getUsername())
                && Objects.equals(getPasswordHash(), that.getPasswordHash());
    }
}
