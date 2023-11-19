package com.epam.training.ticketservice.core.service.implementations;

import com.epam.training.ticketservice.core.dto.MovieDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.model.Movie;
import com.epam.training.ticketservice.core.repository.MovieRepository;
import com.epam.training.ticketservice.core.service.interfaces.MovieServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImplementation implements MovieServiceInterface {
    private final MovieRepository movieRepository;

    @Override
    public void movieCreate(String movieName, String movieGenre, int watchTime) throws AlreadyExists {
        if (movieRepository.findByMovieName(movieName).isPresent()) {
            throw new AlreadyExists("The movie is already existed");
        } else {
            Movie movie = new Movie(movieName, movieGenre, watchTime);
            movieRepository.save(movie);
        }
    }

    @Override
    public void movieUpdate(String movieName, String movieGenre, int watchTime) throws DoesNotExists {
        if (movieRepository.findByMovieName(movieName).isPresent()) {
            Movie movie = movieRepository.findByMovieName(movieName).get();
            movie.setMovieGenre(movieGenre);
            movie.setWatchTime(watchTime);
            movieRepository.save(movie);
        } else {
            throw new DoesNotExists("The movie does not exists");
        }
    }

    @Override
    public void movieDelete(String movieName) throws DoesNotExists {
        if (movieRepository.findByMovieName(movieName).isPresent()) {
            Movie movie = movieRepository.findByMovieName(movieName).get();
            movieRepository.delete(movie);
        } else {
            throw new DoesNotExists("The movie does not exists");
        }
    }

    @Override
    public List<MovieDto> movieList() {
        return movieRepository.findAll().stream().map(MovieDto::new).toList();
    }
}
