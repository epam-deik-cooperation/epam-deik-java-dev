package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByScreening_MovieAndScreening_RoomAndScreening_Date(
            Movie movie, Room room, LocalDateTime date);


}
