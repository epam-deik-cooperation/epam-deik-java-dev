package com.epam.training.ticketservice.core.screening.repository;

import com.epam.training.ticketservice.core.screening.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {

    Optional<Screening> findByMovieAndRoomAndStart(String movie, String room, LocalDateTime start);

    Optional<List<Screening>> findByRoom(String room);
}
