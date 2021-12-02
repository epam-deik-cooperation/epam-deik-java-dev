package hu.unideb.inf.ticketservice.model.user;

public interface UserInterface {

    String getUsername();

    boolean isPrivileged();

    Integer getPasswordHash();

}
