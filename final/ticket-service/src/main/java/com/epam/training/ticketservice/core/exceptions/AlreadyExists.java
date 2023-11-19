package com.epam.training.ticketservice.core.exceptions;

public class AlreadyExists extends Exception {
    public AlreadyExists(String message) {
        super(message);
    }
}
