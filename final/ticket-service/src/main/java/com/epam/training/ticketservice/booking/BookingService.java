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

    public void createBooking(Booking booking) throws NotFoundException, ConflictException {
        if (isBookingValid(booking)) {
            bookingRepository.save(booking);
        }
    }

    private boolean isBookingValid(Booking booking) throws NotFoundException, ConflictException {
        List<Booking> bookingsAtPlace = bookingRepository.findAllByScreening_MovieAndScreening_RoomAndScreening_Date(
                booking.getScreening().getMovie(),
                booking.getScreening().getRoom(),
                booking.getScreening().getDate());

        return isSeatPresent(booking) && isSeatNotBooked(booking, bookingsAtPlace);
    }

    private boolean isSeatPresent(Booking booking) throws NotFoundException {

        List<Seat> notExistentSeatsInRoom = booking.getSeats()
                .stream().filter(x -> x.getRowIndex() > booking.getScreening()
                        .getRoom()
                        .getNumberOfColumns()
                        || x.getColumnIndex() > booking.getScreening()
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

    private boolean isSeatNotBooked(Booking booking, List<Booking> bookingsAtPlace) throws ConflictException {

        List<Seat> bookedSeats = booking.getSeats()
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


    public String showPriceForBooking(Booking booking) throws NotFoundException, ConflictException {

        if (booking.getScreening() == null || !isBookingValid(booking)) {
            throw new NotFoundException("No possible booking found with such properties");
        }


        return String.format("The price for this booking would be %d HUF", booking.getPrice());
    }

}
