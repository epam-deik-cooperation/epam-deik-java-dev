package com.epam.training.ticketservice.core.service.interfaces;

import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.model.Seat;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingServiceInterface {
    String book(String movieName, String roomName, LocalDateTime screeningDate, String seats) throws DoesNotExists;
}
