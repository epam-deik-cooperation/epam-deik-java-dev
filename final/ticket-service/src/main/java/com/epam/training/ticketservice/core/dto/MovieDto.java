package com.epam.training.ticketservice.core.dto;

import com.epam.training.ticketservice.core.model.Movie;
import lombok.Getter;

@Getter
public class MovieDto {
    private final String movieName;
    private final String movieGenre;
    private final int watchTime;

    public MovieDto(Movie movie) {
        movieName = movie.getMovieName();
        movieGenre = movie.getMovieGenre();
        watchTime = movie.getWatchTime();
    }

    @Override
    public String toString() {
        return movieName + " (" + movieGenre + ", " + watchTime + " minutes)";
    }
}
