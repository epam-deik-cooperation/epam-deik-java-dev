package com.epam.training.ticketservice.screening;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private static final String SCREENING_NOT_FOUND = "No screening found with such properties";

    private boolean isDateAvailable(LocalDateTime startDate, LocalDateTime endDate,
                                    LocalDateTime startDateToCheck, LocalDateTime endDateToCheck) {

        return !((startDateToCheck.isBefore(endDate) && startDateToCheck.isAfter(startDate))
                || (endDateToCheck.isBefore(endDate) && endDateToCheck.isAfter(startDate)))
                && startDate != startDateToCheck && endDate != endDateToCheck;
    }

    private boolean validateScreening(Screening screening) {
        return screeningRepository.findAll()
                .stream()
                .filter(x -> x.getRoom().equals(screening.getRoom()))
                .map(y -> isDateAvailable(y.getDate(),
                        y.getDate().plusMinutes(y.getMovie().getLength()),
                        screening.getDate(),
                        screening.getDate().plusMinutes(screening.getMovie().getLength())))
                .filter(boolValue -> boolValue)
                .findFirst().orElse(false);
    }

    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    public void createScreening(Screening newScreening) {
        if (validateScreening(newScreening)) {
            screeningRepository.save(newScreening);
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
