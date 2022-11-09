package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<MovieDto> getMovieList();

    Optional<MovieDto> getMovieByName(String name);

    void createMovie(MovieDto movie);

    boolean deleteMovie(MovieDto movie);

}
