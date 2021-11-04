package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.account.Account;
import com.epam.training.ticketservice.booking.price.PriceCalculator;
import com.epam.training.ticketservice.exception.ConflictException;
import com.epam.training.ticketservice.room.Seat;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ScreeningService screeningService;
    private final PriceCalculator priceCalculator;


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

    public Booking mapToBooking(String movieTitle, String roomName, String date, String seats)
            throws NotFoundException {

        Screening screening = screeningService.getScreeningByProperties(movieTitle, roomName, date);

        List<Seat> seatsToBook = new ArrayList<>();

        Arrays.stream(seats.split(" ")).forEach(x -> {
            List<String> seatSpot = Arrays.asList(x.split(","));
            seatsToBook.add(new Seat(Integer.parseInt(seatSpot.get(0)), Integer.parseInt(seatSpot.get(1))));
        });

        return Booking.builder()
                .screening(screening)
                .seats(seatsToBook)
                .price(priceCalculator.calculate(screening, seatsToBook.size()))
                .build();
    }


    public Booking mapToBooking(String movieTitle, String roomName, String date, String seats, Account account)
            throws NotFoundException {

        Booking booking = mapToBooking(movieTitle, roomName, date, seats);
        booking.setAccount(account);

        return booking;
    }

    public void createBooking(Booking newBooking) throws NotFoundException, ConflictException {
        if (isBookingValid(newBooking)) {
            bookingRepository.save(newBooking);
        }
    }

    public String showPriceForBooking(String movieTitle, String roomName, String date, String seats)
            throws NotFoundException, ConflictException {

        Booking booking = mapToBooking(movieTitle, roomName, date, seats);

        if (booking.getScreening() == null || !isBookingValid(booking)) {
            throw new NotFoundException("No possible booking found with such properties");
        }


        return String.format("The price for this booking would be %d HUF", booking.getPrice());
    }

}
