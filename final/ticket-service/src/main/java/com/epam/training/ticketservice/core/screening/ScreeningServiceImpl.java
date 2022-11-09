package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.entity.Screening;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final RoomService roomService;
    private final MovieService movieService;

    @Override
    public List<ScreeningDto> getScreenings() {
        return screeningRepository.findAll().stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public Optional<ScreeningDto> getScreening(MovieDto movieDto, RoomDto roomDto, LocalDateTime start) {
        var movie = movieDto.getName();
        var room = roomDto.getName();
        Optional<Screening> screening = screeningRepository.findByMovieAndRoomAndStart(movie, room, start);
        return screening.stream().map(this::convert).findFirst();
    }

    @Override
    public void createScreening(ScreeningDto screeningDto) {
        var movie = screeningDto.getMovie().getName();
        var room = screeningDto.getRoom().getName();
        var start = screeningDto.getStart();
        var end = screeningDto.getEnd();
        Screening screening = new Screening(movie, room, start, end);
        screeningRepository.save(screening);
    }

    @Override
    public boolean removeScreening(ScreeningDto screeningDto) {
        var movie = screeningDto.getMovie().getName();
        var room = screeningDto.getRoom().getName();
        var start = screeningDto.getStart();
        Optional<Screening> screening = screeningRepository.findByMovieAndRoomAndStart(movie, room, start);
        if (screening.isPresent()) {
            screeningRepository.delete(screening.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean isOverlappingWithAnother(RoomDto room, LocalDateTime start) {
        Optional<List<Screening>> screenings = screeningRepository.findByRoom(room.getName());
        if (screenings.isPresent()) {
            return !screenings.get().stream().filter(screening -> {
                boolean startOverlaps = screening.getStart().isBefore(ChronoLocalDateTime.from(start));
                boolean endOverlaps = screening.getEndMovie().isAfter(ChronoLocalDateTime.from(start));
                return startOverlaps && endOverlaps;
            }).collect(Collectors.toList()).isEmpty();
        }
        return false;
    }

    @Override
    public boolean isInBreakAfterScreening(RoomDto room, LocalDateTime start) {
        Optional<List<Screening>> screenings = screeningRepository.findByRoom(room.getName());
        if (screenings.isPresent()) {
            return !screenings.get().stream().filter(screening -> {
                boolean startOverlaps = screening.getStart().isBefore(ChronoLocalDateTime.from(start));
                boolean endOverlaps = screening.getEndMovie().plusMinutes(10).isAfter(ChronoLocalDateTime.from(start));
                return startOverlaps && endOverlaps;
            }).collect(Collectors.toList()).isEmpty();
        }
        return false;
    }

    private ScreeningDto convert(Screening screening) {
        var movie = movieService.getMovieByName(screening.getMovie()).get();
        var room = roomService.getRoomByName(screening.getRoom()).get();
        var start = screening.getStart();
        return ScreeningDto.builder().movie(movie).room(room).start(start).build();
    }
}
