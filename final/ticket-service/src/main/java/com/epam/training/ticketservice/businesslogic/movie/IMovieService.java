package com.epam.training.ticketservice.businesslogic.movie;

import com.epam.training.ticketservice.models.movie.Movie;
import java.util.List;
import java.util.Optional;

public interface IMovieService {
    void createMovie(String name, String genre, int length);

    void updateMovie(String name, String genre, int length);

    void deleteMovie(String name);

    List<Movie> listMovies();

    Optional<Movie> getByName(String name);
}
