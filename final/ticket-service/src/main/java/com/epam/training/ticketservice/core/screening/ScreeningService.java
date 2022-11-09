package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    List<ScreeningDto> getScreenings();

    Optional<ScreeningDto> getScreening(MovieDto movie, RoomDto room, LocalDateTime start);

    void createScreening(ScreeningDto screening);

    boolean removeScreening(ScreeningDto screening);

    boolean isOverlappingWithAnother(RoomDto room, LocalDateTime start);

    boolean isInBreakAfterScreening(RoomDto room, LocalDateTime start);
}
