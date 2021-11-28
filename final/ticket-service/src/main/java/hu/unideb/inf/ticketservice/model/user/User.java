package hu.unideb.inf.ticketservice.model.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User extends AbstractUser {

    @Id
    @GeneratedValue
    private Long id;

    public User(String username, String password, boolean privileged) {
        super(username, password, privileged);
    }
}