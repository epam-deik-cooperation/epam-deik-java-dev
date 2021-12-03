package hu.unideb.inf.ticketservice.service.connection.impl;

import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.repository.MovieRepository;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToMovieRepository;

import java.util.List;

public class MovieRepositoryConnection implements ConnectToMovieRepository {

    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;

    public MovieRepositoryConnection(MovieRepository movieRepository, ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
    }

    @Override
    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    @Override
    public void createMovie(Movie movie) {
        List<Movie> movies = listMovies();
        if (!(movies.contains(movie))) {
            movieRepository.save(movie);
        }
    }

    @Override
    public void updateMovie(String name, Movie movie) {
        movieRepository.updateByName(movie.getGenre(),movie.getNumberOfMinutes(),movie.getName());
    }

    @Override
    public void deleteMovie(String name) {
        List<Screening> screeningList = screeningRepository.findAll();
        for (Screening s : screeningList) {
            if (s.getMovie().getName().equals(name)) {
                screeningRepository.delete(s.getRoom(),s.getMovie(),s.getScreeningDate());
            }
        }
        movieRepository.deleteByName(name);
    }

    @Override
    public void updateComponent(String name, PriceComponent component) {
        movieRepository.updateComponent(name,component);
    }
}
