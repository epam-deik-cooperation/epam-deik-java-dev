package com.epam.training.ticketservice.businesslogic.screening;

import com.epam.training.ticketservice.models.movie.Movie;
import com.epam.training.ticketservice.models.room.Room;
import com.epam.training.ticketservice.models.screening.Screening;

import java.time.LocalDateTime;
import java.util.List;

public interface IScreeningService {
    String createScreening(Movie movie, Room room, LocalDateTime dateTime);
    void deleteScreening(Movie movie, Room room, LocalDateTime dateTime);
    List<Screening> listScreenings();
}