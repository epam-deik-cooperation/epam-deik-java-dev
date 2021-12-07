package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.models.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByName(String name);
}
