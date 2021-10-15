package com.epam.training.ticketservice.movie;

import com.epam.training.ticketservice.exception.InvalidMovieException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final static String MOVIE_NOT_FOUND = "Given movie title does not exist.";


    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public void createMovie(Movie newMovie) {
        movieRepository.save(newMovie);
    }

    public void updateMovie(Movie movieToUpdate) throws InvalidMovieException {
        if(movieRepository.existsById(movieToUpdate.getTitle())) {
            movieRepository.save(movieToUpdate);
        } else {
            throw new InvalidMovieException(MOVIE_NOT_FOUND);
        }
    }

    public void deleteMovie(String movieTitle) throws InvalidMovieException {
        if(movieRepository.existsById(movieTitle)) {
            movieRepository.deleteById(movieTitle);
        } else {
            throw new InvalidMovieException(MOVIE_NOT_FOUND);
        }
    }




}
