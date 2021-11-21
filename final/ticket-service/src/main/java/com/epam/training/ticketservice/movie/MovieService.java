package com.epam.training.ticketservice.movie;


import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private static final String MOVIE_NOT_FOUND = "Movie with given title not found.";
    private static final String MOVIE_ALREADY_EXIST = "Movie with given title already exists";

    private final MovieRepository movieRepository;


    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie findByTitle(String title) throws NotFoundException {

        Movie movie = movieRepository.findByTitleContainingIgnoreCase(title);

        if (movie != null) {
            return movieRepository.findByTitleContainingIgnoreCase(title);
        } else {
            throw new NotFoundException(MOVIE_NOT_FOUND);
        }
    }

    public Movie mapToMovie(String title, String genre, int length) {
        return Movie.builder()
                .title(title)
                .genre(genre)
                .length(length)
                .build();
    }

    public void createMovie(Movie movie) throws AlreadyExistsException {
        if (!movieRepository.existsByTitleContainingIgnoreCase(movie.getTitle())) {
            movieRepository.save(movie);
        } else {
            throw new AlreadyExistsException(MOVIE_ALREADY_EXIST);
        }
    }

    public void updateMovie(Movie movie) throws NotFoundException {
        if (movieRepository.existsByTitleContainingIgnoreCase(movie.getTitle())) {
            movieRepository.update(movie.getTitle(), movie.getGenre(), movie.getLength());
        } else {
            throw new NotFoundException(MOVIE_NOT_FOUND);
        }
    }

    public void deleteMovie(String title) throws NotFoundException {
        if (movieRepository.existsByTitleContainingIgnoreCase(title)) {
            movieRepository.deleteByTitleContainingIgnoreCase(title);
        } else {
            throw new NotFoundException(MOVIE_NOT_FOUND);
        }
    }


}
