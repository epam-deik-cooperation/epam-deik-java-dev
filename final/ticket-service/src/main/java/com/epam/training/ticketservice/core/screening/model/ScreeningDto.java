package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@EqualsAndHashCode
public class ScreeningDto {

    private final MovieDto movie;
    private final RoomDto room;
    private final LocalDateTime start;
    private final LocalDateTime end;

    @Override
    public String toString() {
        return movie.getName() + " (" + movie.getTheme() + ", "
                + movie.getLength() + " minutes), screened in room " + room.getName() + ", at " + start;
    }
}
