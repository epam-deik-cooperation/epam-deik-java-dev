package com.epam.training.ticketservice.movie;


import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final static String MOVIE_NOT_FOUND = "Movie with given title not found.";


    public List<Movie> getAllMovies() {
            return movieRepository.findAll();
    }

    public void createMovie(Movie newMovie) {
            movieRepository.save(newMovie);
        }

    public void updateMovie(Movie movieToUpdate) throws NotFoundException {
        if (movieRepository.existsByTitle(movieToUpdate.getTitle())) {
            movieRepository.update(movieToUpdate.getTitle(), movieToUpdate.getGenre(), movieToUpdate.getLength());
        } else {
            throw new NotFoundException(MOVIE_NOT_FOUND);
        }
    }

    public void deleteMovie(String movieTitle) throws NotFoundException {
        if (movieRepository.existsByTitle(movieTitle)) {
            movieRepository.deleteByTitle(movieTitle);
        } else {
            throw new NotFoundException(MOVIE_NOT_FOUND);
        }
    }


}
