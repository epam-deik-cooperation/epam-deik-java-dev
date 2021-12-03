package hu.unideb.inf.ticketservice.model.user;

public class DefaultUser extends User  {


    public DefaultUser() {
        this.username = "default";
        this.password = "default".hashCode();
        this.privileged = false;
    }
}
