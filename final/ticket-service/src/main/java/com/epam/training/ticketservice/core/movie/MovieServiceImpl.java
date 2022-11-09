package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.entity.Movie;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<MovieDto> getMovieList() {
        return movieRepository.findAll().stream().map(movie -> convert(movie)).collect(Collectors.toList());
    }

    @Override
    public Optional<MovieDto> getMovieByName(String name) {
        return movieRepository.findByName(name).stream().map(this::convert).findFirst();
    }

    @Override
    public void createMovie(MovieDto movie) {
        Movie movie1 = new Movie(movie.getName(), movie.getTheme(), movie.getLength());
        movieRepository.save(movie1);
    }

    @Override
    public boolean deleteMovie(MovieDto movieDto) {
        Optional<Movie> movie1 = movieRepository.findByName(movieDto.getName());
        if (movie1.isPresent()) {
            movieRepository.delete(movie1.get());
            return true;
        }
        return false;
    }

    private MovieDto convert(Movie movie) {
        return MovieDto.builder().name(movie.getName()).theme(movie.getTheme()).length(movie.getLength()).build();
    }
}
