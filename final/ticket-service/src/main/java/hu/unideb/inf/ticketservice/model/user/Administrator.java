package hu.unideb.inf.ticketservice.model.user;

import hu.unideb.inf.ticketservice.service.AdminCredentialsProvider;

public class Administrator extends AbstractUser {

    public Administrator(AdminCredentialsProvider credentialsProvider) {
        super(credentialsProvider.getUsername(), credentialsProvider.getPassword(), true);
    }
}
