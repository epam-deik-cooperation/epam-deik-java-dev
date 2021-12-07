package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.models.movie.Movie;
import com.epam.training.ticketservice.models.room.Room;
import com.epam.training.ticketservice.models.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    List<Screening> findAll();

    Optional<Screening> findByMovieAndRoomAndDateTime(Movie movie, Room room, LocalDateTime dateTime);
}
