package com.epam.training.taxi.exception;

public class NegativeDistanceException extends RuntimeException {

    public NegativeDistanceException() {
        super("Distance cannot be negative");
    }
}
