package com.epam.training.ticketservice.core.service.interfaces;

public interface MovieServiceInterface {
    String movieCreate(String movieName, String movieGenre, int watchTime);
    String movieUpdate(String movieName, String movieGenre, int watchTime);
    String movieDelete(String movieName);
    String movieList();
}
