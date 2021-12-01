package hu.unideb.inf.ticketservice.repository.init;

import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

//Uncomment the line below to initialize repository
@Repository
public class MovieRepositoryInitializer {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieRepositoryInitializer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    private final List<Movie> initialMovies = List.of(
            new Movie("The Nun","horror",135),
                new Movie("In Time","sc-fi",101),
                new Movie("The Iron Man","action",98)
        );

    @PostConstruct
    private void saveMovies() {
        initialMovies.forEach(movieRepository::save);
    }

}
