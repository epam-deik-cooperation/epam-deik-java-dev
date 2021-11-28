package hu.unideb.inf.ticketservice.model.user;

public class DefaultUser extends AbstractUser {

    private static final String USERNAME = "default";
    private static final String PASSWORD = "default";

    public DefaultUser() {
        super(USERNAME,PASSWORD,false);
    }
}
