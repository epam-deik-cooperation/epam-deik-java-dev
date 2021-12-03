package hu.unideb.inf.ticketservice.repository;

import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MovieRepository extends Repository<Movie, Long> {
    List<Movie> findAll();

    void save(Movie movie);

    void deleteByName(String name);

    @Modifying
    @Query("update Movie e set e.genre = ?1, e.movieLength = ?2, e.name = ?3 where e.name = ?3")
    void updateByName(String genre, Integer numberOfMinutes, String name);

    @Modifying
    @Query("update Movie e set e.component = ?2 where e.name = ?1")
    void updateComponent(String name, PriceComponent component);
}
