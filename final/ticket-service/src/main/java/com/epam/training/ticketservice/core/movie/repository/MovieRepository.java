package com.epam.training.ticketservice.core.movie.repository;

import com.epam.training.ticketservice.core.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Optional<Movie> findByName(String name);
}
