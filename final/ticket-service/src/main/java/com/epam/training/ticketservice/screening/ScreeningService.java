package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.exception.DateConflictException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private static final String SCREENING_NOT_FOUND = "No screening found with such properties";
    private static final String BREAK_TIME_CONFLICT = "This would start in the break period after another screening in this room";
    private static final String OVERLAPPING_TIME = "There is an overlapping screening";

    private boolean isDateAvailable(LocalDateTime startDate, LocalDateTime endDate,
                                    LocalDateTime startDateToCheck, LocalDateTime endDateToCheck,
                                    int breakTime) {

        return !((startDateToCheck.isBefore(endDate.plusMinutes(breakTime)) && startDateToCheck.isAfter(startDate))
                || (endDateToCheck.isBefore(endDate) && endDateToCheck.isAfter(startDate)))
                && startDate != startDateToCheck && endDate != endDateToCheck;
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
                    .filter(boolValue -> boolValue)
                    .findFirst().orElse(false);
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
                    .filter(boolValue -> boolValue)
                    .findFirst().orElse(false);
        }
    }

    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    public void createScreening(Screening newScreening) throws DateConflictException {
        if (validateScreening(newScreening)) {
            if (validateScreening(newScreening, 10)) {
                screeningRepository.save(newScreening);
            } else {
                throw new DateConflictException(BREAK_TIME_CONFLICT);
            }
        } else {
            throw new DateConflictException(OVERLAPPING_TIME);
        }
    }

    public void deleteScreening(String movieTitle, String roomName, LocalDateTime date) throws NotFoundException {
        if (screeningRepository.existsByMovie_TitleAndRoom_NameAndDate(movieTitle, roomName, date)) {
            screeningRepository.deleteByMovie_TitleAndRoom_NameAndDate(movieTitle, roomName, date);
        } else {
            throw new NotFoundException(SCREENING_NOT_FOUND);
        }
    }


}
