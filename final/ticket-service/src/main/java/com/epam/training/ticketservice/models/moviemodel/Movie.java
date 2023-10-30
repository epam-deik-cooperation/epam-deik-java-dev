package com.epam.training.ticketservice.models.moviemodel;

import lombok.Data;

@Data
public class Movie {
    private String name;
    private String genre;
    private int lengthInTime;

    public Movie(String name, String genre, int lengthInTime) {
        this.name = name;
        this.genre = genre;
        this.lengthInTime = lengthInTime;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.genre + ", " + this.lengthInTime + " minutes)";
    }
}
