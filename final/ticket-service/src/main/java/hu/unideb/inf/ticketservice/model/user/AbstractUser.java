package hu.unideb.inf.ticketservice.model.user;

import java.util.Objects;

public abstract class AbstractUser {
    protected String username;
    protected Integer password;
    protected boolean privileged;

    public AbstractUser(String username, String password, boolean privileged) {
        this.username = username;
        this.password = password.hashCode();
        this.privileged = privileged;
    }

    public String getUsername() {
        return username;
    }

    public Integer getPasswordHash() {
        return password;
    }

    public boolean isPrivileged() {
        return privileged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractUser that = (AbstractUser) o;
        return privileged == that.privileged && Objects.equals(username, that.username)
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, privileged);
    }
}
