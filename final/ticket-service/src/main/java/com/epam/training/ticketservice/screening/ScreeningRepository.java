package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    boolean existsByMovie_TitleAndRoom_NameAndDate(String movieTitle, String roomName, LocalDate date);

    void deleteByMovie_TitleAndRoom_NameAndDate(String movieTitle, String roomName, LocalDate date);

}
