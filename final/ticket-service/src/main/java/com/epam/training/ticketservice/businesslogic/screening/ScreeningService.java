package com.epam.training.ticketservice.businesslogic.screening;

import com.epam.training.ticketservice.models.movie.Movie;
import com.epam.training.ticketservice.models.room.Room;
import com.epam.training.ticketservice.models.screening.Screening;
import com.epam.training.ticketservice.repositories.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService implements IScreeningService {

    private ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    private boolean isOverlapping(Movie movie, Room room, LocalDateTime dateTime) {
        for (var s : screeningRepository.findAll()) {
            if (s.getRoom().equals(room)) {
                LocalDateTime start = s.getDateTime();
                LocalDateTime end = start.plusMinutes(s.getMovie().getMinutes());
                if (dateTime.isAfter(start) && dateTime.isBefore(end)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOverlappingBreakTime(Movie movie, Room room, LocalDateTime dateTime) {
        for (Screening s : screeningRepository.findAll()) {
            if (s.getRoom().equals(room)) {
                LocalDateTime start = s.getDateTime().plusMinutes(s.getMovie().getMinutes());
                LocalDateTime end = start.plusMinutes(10);
                if (dateTime.isAfter(start) && dateTime.isBefore(end)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String createScreening(Movie movie, Room room, LocalDateTime dateTime) {
        if (isOverlapping(movie,room, dateTime)) {
            return "There is an overlapping screening";
        }
        if (isOverlappingBreakTime(movie,room, dateTime)) {
            return "This would start in the break period after another screening in this room";
        }
        if (movie != null && room != null) {
            screeningRepository.save(new Screening(movie, room, dateTime));
        }
        return null;
    }

    @Override
    public void deleteScreening(Movie movie, Room room, LocalDateTime dateTime) {
        var screening = get(movie, room, dateTime);
        screening.ifPresent(value -> screeningRepository.delete(value));
    }

    @Override
    public List<Screening> listScreenings() {
        return screeningRepository.findAll();
    }

    private Optional<Screening> get(Movie movie, Room room, LocalDateTime datetime) {
        return screeningRepository.findByMovieAndRoomAndDateTime(movie, room, datetime);
    }
}
