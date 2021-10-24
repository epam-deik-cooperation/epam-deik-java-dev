package com.epam.training.ticketservice.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private boolean isBookingValid(Booking newBooking) {
        List<Booking> bookingsAtPlace = bookingRepository.findAllByScreening_MovieAndScreening_RoomAndScreening_Date(
                newBooking.getScreening().getMovie(),
                newBooking.getScreening().getRoom(),
                newBooking.getScreening().getDate());

        return newBooking.getSeats()
                .stream()
                .noneMatch(x -> (bookingsAtPlace.stream()
                        .map(Booking::getSeats)
                        .map(y -> y.contains(x))
                        .findAny()
                        .isPresent()));
    }

    public void create(Booking newBooking) {
        if (isBookingValid(newBooking)) {
            bookingRepository.save(newBooking);
        }
    }


}
