package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.exception.ConflictException;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.room.RoomService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;
    private final RoomService roomService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final String SCREENING_NOT_FOUND = "No screening found with such properties";
    private static final String BREAK_TIME_CONFLICT =
            "This would start in the break period after another screening in this room";
    private static final String OVERLAPPING_TIME = "There is an overlapping screening";

    private boolean isDateAvailable(LocalDateTime startDate, LocalDateTime endDate,
                                    LocalDateTime startDateToCheck, LocalDateTime endDateToCheck,
                                    int breakTime) {

        return !((startDateToCheck.isBefore(endDate.plusMinutes(breakTime)) && startDateToCheck.isAfter(startDate))
                || (endDateToCheck.isBefore(endDate) && endDateToCheck.isAfter(startDate)))
                && !(startDate.equals(startDateToCheck) || endDate.equals(endDateToCheck));
    }

    private boolean validateScreening(Screening screening) {

        List<Screening> screeningsInSameRoom = screeningRepository.findAll()
                .stream()
                .filter(x -> x.getRoom().equals(screening.getRoom()))
                .collect(Collectors.toList());

        if (screeningsInSameRoom.isEmpty()) {
            return true;
        } else {
            return screeningsInSameRoom.stream()
                    .map(y -> isDateAvailable(y.getDate(),
                            y.getDate().plusMinutes(y.getMovie().getLength()),
                            screening.getDate(),
                            screening.getDate().plusMinutes(screening.getMovie().getLength()), 0))
                    .filter(boolValue -> !boolValue)
                    .findFirst().orElse(true);
        }
    }

    private boolean validateScreening(Screening screening, int breakTime) {

        List<Screening> screeningsInSameRoom = screeningRepository.findAll()
                .stream()
                .filter(x -> x.getRoom().equals(screening.getRoom()))
                .collect(Collectors.toList());

        if (screeningsInSameRoom.isEmpty()) {
            return true;
        } else {
            return screeningsInSameRoom.stream()
                    .map(y -> isDateAvailable(y.getDate(),
                            y.getDate().plusMinutes(y.getMovie().getLength()),
                            screening.getDate(),
                            screening.getDate().plusMinutes(screening.getMovie().getLength()), breakTime))
                    .filter(boolValue -> !boolValue)
                    .findFirst().orElse(true);
        }
    }

    public Screening mapToScreening(String movieTitle, String roomName, String date)
            throws NotFoundException {

        LocalDateTime parsedDate = LocalDateTime.parse(date, formatter);

        return Screening.builder()
                .movie(movieService.findByTitle(movieTitle))
                .room(roomService.findByName(roomName))
                .date(parsedDate)
                .build();
    }

    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    public Screening getScreeningByProperties(String movieTitle,
                                              String roomName,
                                              String date) throws NotFoundException {
        Screening screening =
                screeningRepository.findByMovie_TitleContainingIgnoreCaseAndRoom_NameContainingIgnoreCaseAndDate(
                        movieTitle,
                        roomName,
                        LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        if (screening != null) {
            return screening;
        } else {
            throw new NotFoundException(SCREENING_NOT_FOUND);
        }
    }


    public void createScreening(Screening newScreening) throws ConflictException {
        if (validateScreening(newScreening)) {
            if (validateScreening(newScreening, 10)) {
                screeningRepository.save(newScreening);
            } else {
                throw new ConflictException(BREAK_TIME_CONFLICT);
            }
        } else {
            throw new ConflictException(OVERLAPPING_TIME);
        }
    }

    public void deleteScreening(Screening screening) throws NotFoundException {
        if (screeningRepository.existsByMovie_TitleContainingIgnoreCaseAndRoom_NameContainingIgnoreCaseAndDate(
                screening.getMovie().getTitle(),
                screening.getRoom().getName(),
                screening.getDate())) {

            screeningRepository.deleteByMovie_TitleContainingIgnoreCaseAndRoom_NameContainingIgnoreCaseAndDate(
                    screening.getMovie().getTitle(),
                    screening.getRoom().getName(),
                    screening.getDate());

        } else {
            throw new NotFoundException(SCREENING_NOT_FOUND);
        }
    }


}
