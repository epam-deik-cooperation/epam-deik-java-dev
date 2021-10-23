package com.epam.training.ticketservice.movie;


import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {


    private final MovieRepository movieRepository;

    private static final String MOVIE_NOT_FOUND = "Movie with given title not found.";
    private static final String MOVIE_ALREADY_EXIST = "Movie with given title already exists";


    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public void createMovie(Movie newMovie) throws AlreadyExistsException {
        if (!movieRepository.existsByTitleContainingIgnoreCase(newMovie.getTitle())) {
            movieRepository.save(newMovie);
        } else {
            throw new AlreadyExistsException(MOVIE_ALREADY_EXIST);
        }
    }

    public void updateMovie(Movie movieToUpdate) throws NotFoundException {
        if (movieRepository.existsByTitleContainingIgnoreCase(movieToUpdate.getTitle())) {
            movieRepository.update(movieToUpdate.getTitle(), movieToUpdate.getGenre(), movieToUpdate.getLength());
        } else {
            throw new NotFoundException(MOVIE_NOT_FOUND);
        }
    }

    public void deleteMovie(String movieTitle) throws NotFoundException {
        if (movieRepository.existsByTitleContainingIgnoreCase(movieTitle)) {
            movieRepository.deleteByTitleContainingIgnoreCase(movieTitle);
        } else {
            throw new NotFoundException(MOVIE_NOT_FOUND);
        }
    }

    public Movie findByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }


}
