package com.epam.training.ticketservice.core.service.implementations;

import com.epam.training.ticketservice.core.dto.ScreeningDto;
import com.epam.training.ticketservice.core.exceptions.BreakPeriod;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.exceptions.Overlap;
import com.epam.training.ticketservice.core.model.Movie;
import com.epam.training.ticketservice.core.model.Room;
import com.epam.training.ticketservice.core.model.Screening;
import com.epam.training.ticketservice.core.repository.MovieRepository;
import com.epam.training.ticketservice.core.repository.RoomRepository;
import com.epam.training.ticketservice.core.repository.ScreeningRepository;
import com.epam.training.ticketservice.core.service.interfaces.ScreeningServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImplementation implements ScreeningServiceInterface {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    @Override
    public void createScreening(String movieName, String roomName, LocalDateTime screeningDate)
            throws DoesNotExists, Overlap, BreakPeriod {
        Optional<Movie> movie = movieRepository.findByMovieName(movieName);
        Optional<Room> room = roomRepository.findByRoomName(roomName);
        checkMovieAndRoomExistence(movie, room);
        Screening returnScreening = new Screening(movie.get(), room.get(), screeningDate);
        canCreateScreening(returnScreening);
        screeningRepository.save(returnScreening);
    }

    private void canCreateScreening(Screening returnScreening) throws Overlap, BreakPeriod {
        Optional<Screening> screeningList = screeningRepository.findScreeningByRoom(returnScreening.getRoom());

        if (screeningList.isEmpty()) {
            return;
        }

        for (Screening iterator : screeningList.stream().toList()) {
            LocalDateTime screeningStart = returnScreening.getScreeningDate();
            LocalDateTime screeningEnd = screeningStart.plusMinutes(returnScreening.getMovie().getWatchTime());
            LocalDateTime screeningBreakPeriod = screeningEnd.plusMinutes(10);

            LocalDateTime iteratorStart = iterator.getScreeningDate();
            LocalDateTime iteratorEnd = iteratorStart.plusMinutes(iterator.getMovie().getWatchTime());
            LocalDateTime iteratorBreakPeriod = iteratorEnd.plusMinutes(10);

            boolean isScreeningBetweenIterators = isBetween(screeningStart, iteratorStart, iteratorEnd)
                    || isBetween(screeningEnd, iteratorStart, iteratorEnd)
                    || isInside(iteratorStart, iteratorEnd, screeningStart, screeningEnd);
            boolean isScreeningBeforeIteratorBreak = isBefore(screeningEnd, iteratorStart)
                    && isAfter(screeningBreakPeriod, iteratorStart);
            boolean isScreeningAfterIteratorEnd = isAfter(screeningStart, iteratorEnd)
                    && isBefore(screeningStart, iteratorBreakPeriod);

            if (isScreeningBetweenIterators || isEqual(screeningStart, iteratorStart)
                    || isEqual(screeningEnd, iteratorEnd)) {
                throw new Overlap("There is an overlapping screening");
            } else if (isScreeningBeforeIteratorBreak || isScreeningAfterIteratorEnd) {
                throw new BreakPeriod("This would start in the break period after another screening in this room");
            }
        }
    }

    private static boolean isBetween(LocalDateTime dateTime, LocalDateTime start, LocalDateTime end) {
        return dateTime.isAfter(start) && dateTime.isBefore(end);
    }

    private static boolean isInside(LocalDateTime start, LocalDateTime end,
                                    LocalDateTime innerStart, LocalDateTime innerEnd) {
        return innerStart.isAfter(start) && innerEnd.isBefore(end);
    }

    private static boolean isEqual(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isEqual(dateTime2);
    }

    private static boolean isBefore(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isBefore(dateTime2);
    }

    private static boolean isAfter(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isAfter(dateTime2);
    }

    @Override
    public void deleteScreening(String movieName, String roomName, LocalDateTime screeningDate) throws DoesNotExists {
        Optional<Movie> movie = movieRepository.findByMovieName(movieName);
        Optional<Room> room = roomRepository.findByRoomName(roomName);
        checkMovieAndRoomExistence(movie, room);
        if (screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(movie.get(),
                room.get(), screeningDate).isPresent()) {
            Screening screening = screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(movie.get(),
                    room.get(), screeningDate).get();
            screeningRepository.delete(screening);
        } else {
            throw new DoesNotExists("The given screening does not exists");
        }
    }

    private void checkMovieAndRoomExistence(Optional<Movie> movie, Optional<Room> room) throws DoesNotExists {
        if (movie.isEmpty() && room.isEmpty()) {
            throw new DoesNotExists("The given movie and room do not exist");
        } else if (room.isEmpty()) {
            throw new DoesNotExists("The given room does not exist");
        } else if (movie.isEmpty()) {
            throw new DoesNotExists("The given movie does not exist");
        }
    }

    @Override
    public List<ScreeningDto> listScreenings() {
        return screeningRepository.findAll().stream().map(ScreeningDto::new).toList();
    }
}
