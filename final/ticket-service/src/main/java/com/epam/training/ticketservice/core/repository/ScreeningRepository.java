package com.epam.training.ticketservice.core.repository;

import com.epam.training.ticketservice.core.model.Movie;
import com.epam.training.ticketservice.core.model.Room;
import com.epam.training.ticketservice.core.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    Optional<Screening> findScreeningByMovieAndRoomAndScreeningDate(Movie movie,
                                                                    Room room, LocalDateTime screeningDate);

    Optional<Screening> findScreeningByRoom(Room room);
}
