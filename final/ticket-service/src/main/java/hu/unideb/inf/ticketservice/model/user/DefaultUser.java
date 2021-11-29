package hu.unideb.inf.ticketservice.model.user;

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
}
