package com.epam.training.ticketservice.screening;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private static final String SCREENING_NOT_FOUND = "No screening found with such properties";

    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    public void createScreening(Screening newScreening) {
        /* TODO:
            - Validator */
        screeningRepository.save(newScreening);
    }

    public void deleteScreening(String movieTitle, String roomName, LocalDate date) throws NotFoundException {
        if (screeningRepository.existsByMovie_TitleAndRoom_NameAndDate(movieTitle, roomName, date)) {
            screeningRepository.deleteByMovie_TitleAndRoom_NameAndDate(movieTitle, roomName, date);
        } else {
            throw new NotFoundException(SCREENING_NOT_FOUND);
        }
    }

}
