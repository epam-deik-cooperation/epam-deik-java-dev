package com.epam.training.ticketservice.core.service.implementations;

import com.epam.training.ticketservice.core.dto.ScreeningDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
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

    private void checkMovieAndRoomExistence(Optional<Movie> movie, Optional<Room> room) throws DoesNotExists {
        if (movie.isEmpty() && room.isEmpty()) {
            throw new DoesNotExists("The given movie and room do not exist");
        } else if (room.isEmpty()) {
            throw new DoesNotExists("The given room does not exist");
        } else if (movie.isEmpty()) {
            throw new DoesNotExists("The given movie does not exist");
        }
    }

    private void canCreateScreening(Screening returnScreening) throws Overlap, BreakPeriod {
        List<Screening> screeningList = screeningRepository.findScreeningByRoom(returnScreening.getRoom())
                .stream().toList();
        if (screeningList.isEmpty()) {
            return;
        }

        for (Screening iterator : screeningList) {
            LocalDateTime screeningStart = returnScreening.getScreeningDate();
            LocalDateTime screeningEnd = returnScreening.getScreeningDate()
                    .plusMinutes(returnScreening.getMovie().getWatchTime());
            LocalDateTime screeningBreakPeriod = returnScreening.getScreeningDate()
                    .plusMinutes(returnScreening.getMovie().getWatchTime() + 10);

            LocalDateTime iteratorStart = iterator.getScreeningDate();
            LocalDateTime iteratorEnd = iterator.getScreeningDate()
                    .plusMinutes(iterator.getMovie().getWatchTime());
            LocalDateTime iteratorBreakPeriod = iterator.getScreeningDate()
                    .plusMinutes(iterator.getMovie().getWatchTime() + 10);

            if (screeningStart.isAfter(iteratorStart) && screeningStart.isBefore(iteratorEnd)
                    || screeningEnd.isAfter(iteratorStart) && screeningEnd.isBefore(iteratorEnd)
                    || screeningStart.isAfter(iteratorStart) && screeningEnd.isBefore(iteratorEnd)
                    || (screeningEnd.isEqual(iteratorEnd) || screeningStart.isEqual(iteratorStart))
                    || (screeningEnd.isEqual(iteratorStart)) || (screeningStart.isEqual(iteratorEnd))) {
                throw new Overlap("There is an overlapping screening");
            } else if (screeningEnd.isBefore(iteratorStart) && screeningBreakPeriod.isAfter(iteratorStart)
                    || screeningStart.isAfter(iteratorEnd) && screeningStart.isBefore(iteratorBreakPeriod)) {
                throw new BreakPeriod("This would start in the break period after another screening in this room");
            }
        }
    }

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

    @Override
    public List<ScreeningDto> listScreenings() {
        return screeningRepository.findAll().stream().map(ScreeningDto::new).toList();
    }
}
