package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.exception.ConflictException;
import com.epam.training.ticketservice.room.Seat;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private boolean isSeatPresent(Booking newBooking) throws NotFoundException {

        List<Seat> notExistentSeatsInRoom = newBooking.getSeats()
                .stream().filter(x -> x.getRowIndex() > newBooking.getScreening()
                        .getRoom()
                        .getNumberOfColumns()
                        || x.getColumnIndex() > newBooking.getScreening()
                        .getRoom()
                        .getNumberOfRows())
                .collect(Collectors.toList());

        List<String> seatList = new ArrayList<>();

        notExistentSeatsInRoom.forEach(x ->
                seatList.add(String.format("(%d,%d)", x.getRowIndex(), x.getColumnIndex())));

        if (notExistentSeatsInRoom.isEmpty()) {
            return true;
        } else {
            throw new NotFoundException(String.format("Seat %s does not exist in this room",
                    String.join(", ", seatList)));
        }
    }

    private boolean isSeatNotBooked(Booking newBooking, List<Booking> bookingsAtPlace) throws ConflictException {

        List<Seat> bookedSeats = newBooking.getSeats()
                .stream()
                .filter(x -> bookingsAtPlace.stream()
                        .map(Booking::getSeats)
                        .anyMatch(y -> y.contains(x)))
                .collect(Collectors.toList());

        List<String> seatList = new ArrayList<>();

        bookedSeats.forEach(x ->
                seatList.add(String.format("(%d,%d)", x.getRowIndex(), x.getColumnIndex())));

        if (bookedSeats.isEmpty()) {
            return true;
        } else {
            throw new ConflictException(String.format("Seat %s is already taken",
                    String.join(", ", seatList)));
        }
    }

    private boolean isBookingValid(Booking newBooking) throws NotFoundException, ConflictException {
        List<Booking> bookingsAtPlace = bookingRepository.findAllByScreening_MovieAndScreening_RoomAndScreening_Date(
                newBooking.getScreening().getMovie(),
                newBooking.getScreening().getRoom(),
                newBooking.getScreening().getDate());

        return isSeatPresent(newBooking) && isSeatNotBooked(newBooking, bookingsAtPlace);
    }

    public void createBooking(Booking newBooking) throws NotFoundException, ConflictException {
        if (isBookingValid(newBooking)) {
            bookingRepository.save(newBooking);
        }
    }


}
