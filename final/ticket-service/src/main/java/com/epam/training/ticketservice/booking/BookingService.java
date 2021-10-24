package com.epam.training.ticketservice.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public void create(Booking newBooking) {
        bookingRepository.save(newBooking);
    }


}
