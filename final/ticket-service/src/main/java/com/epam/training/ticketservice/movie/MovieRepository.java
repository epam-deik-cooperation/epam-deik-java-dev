package com.epam.training.ticketservice.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Modifying
    @Query("UPDATE Movie m SET m.genre = :genre, m.length = :length WHERE m.title = :title")
    void update(@Param("title") String title, @Param("genre") String genre, @Param("length") Integer length);

    void deleteByTitle(String title);

    boolean existsByTitle(String title);

    Movie findByTitle(String title);

}
