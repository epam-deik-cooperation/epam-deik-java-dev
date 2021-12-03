package hu.unideb.inf.ticketservice.model.user;

import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;

import java.util.Objects;

public class Administrator extends User  {

    public Administrator(AdminCredentialsProvider credentialsProvider) {
        username = credentialsProvider.getUsername();
        password = credentialsProvider.getPassword().hashCode();
        privileged = true;
    }
}
