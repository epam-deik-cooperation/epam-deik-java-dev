package hu.unideb.inf.ticketservice.service.impl;

import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.service.DateValidationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DateValidationImpl implements DateValidationService {

    private static final Integer TIME_NEEDED_IN_BREAK = 10;
    private static final String PATTERN = "yyyy-MM-dd HH:mm";

    @Override
    public boolean isDateValid(String dateString) {
        boolean valid = true;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
            LocalDateTime.parse(dateString,formatter);
        } catch (Exception e) {
            valid = false;
        }
        return valid;
    }

    @Override
    public boolean isDateOverlapping(String givenDate, List<Screening> screenings, Room room, Integer movieLength) {
        List<Screening> screeningsInThatRoom = getScreeningsByRoom(screenings,room);
        if (!screeningsInThatRoom.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
            LocalDateTime actualStartDate = LocalDateTime.parse(givenDate, formatter);
            LocalDateTime actualEndDate = actualStartDate.plusMinutes(movieLength);

            for (Screening s : screeningsInThatRoom) {
                LocalDateTime start = LocalDateTime.parse(s.getScreeningDate(), formatter);
                LocalDateTime end = start.plusMinutes(s.getMovie().getNumberOfMinutes());
                if (isInsideGivenDates(actualStartDate, actualEndDate, start, end)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public boolean isDateInsideBreakTime(String givenDate, List<Screening> screenings, Room room, Integer movieLength) {
        List<Screening> screeningsInThatRoom = getScreeningsByRoom(screenings,room);
        if (!screeningsInThatRoom.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
            LocalDateTime actualStartDate = LocalDateTime.parse(givenDate, formatter);
            LocalDateTime actualEndDate = actualStartDate.plusMinutes(movieLength);

            for (Screening s : screeningsInThatRoom) {
                LocalDateTime start = LocalDateTime.parse(s.getScreeningDate(), formatter);
                LocalDateTime end = start.plusMinutes(s.getMovie().getNumberOfMinutes())
                        .plusMinutes(TIME_NEEDED_IN_BREAK);
                if (isInsideGivenDates(actualStartDate, actualEndDate, start, end)) {
                    return true;
                }
            }

        }
        return false;
    }


    private List<Screening> getScreeningsByRoom(List<Screening> screenings, Room room) {
        return screenings.stream()
                .filter(s -> s.getRoom().equals(room))
                .collect(Collectors.toList());
    }

    private boolean isInsideGivenDates(LocalDateTime toBeCheckedStart,
                                       LocalDateTime toBeCheckedEnd, LocalDateTime start, LocalDateTime end) {
        if (toBeCheckedStart.isBefore(end) && toBeCheckedStart.isAfter(start)) {
            return true;
        } else {
            return toBeCheckedEnd.isBefore(end) && toBeCheckedEnd.isAfter(start);
        }
    }
}
