package com.epam.training.ticketservice.core.service.interfaces;

import com.epam.training.ticketservice.core.dto.ScreeningDto;
import com.epam.training.ticketservice.core.exceptions.BreakPeriod;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.exceptions.Overlap;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningServiceInterface {
    void createScreening(String movieName, String roomName, LocalDateTime screeningDate)
            throws DoesNotExists, Overlap, BreakPeriod;

    void deleteScreening(String movieName, String roomName, LocalDateTime screeningDate) throws DoesNotExists;

    List<ScreeningDto> listScreenings();
}
