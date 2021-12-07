package com.epam.training.ticketservice.businesslogic.movie;

import com.epam.training.ticketservice.models.movie.Movie;
import com.epam.training.ticketservice.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService implements IMovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void createMovie(String name, String genre, int length) {
        movieRepository.save(new Movie(name, genre, length));
    }

    @Override
    public void updateMovie(String name, String genre, int length) {
        var movie = getByName(name);
        if (movie.isPresent()) {
            movie.get().update(name, genre, length);
            movieRepository.save(movie.get());
        }
    }

    @Override
    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    @Override
    public void deleteMovie(String name) {
        var movie = getByName(name);
        movie.ifPresent(value -> movieRepository.delete(value));
    }

    @Override
    public Optional<Movie> getByName(String name) {
        return movieRepository.findByName(name);
    }
}
