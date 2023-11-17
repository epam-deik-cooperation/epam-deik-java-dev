package com.epam.training.ticketservice.core.repository;

import com.epam.training.ticketservice.core.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByMovieName(String movieName);
}
