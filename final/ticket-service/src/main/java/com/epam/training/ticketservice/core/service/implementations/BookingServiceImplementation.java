package com.epam.training.ticketservice.core.service.implementations;

import com.epam.training.ticketservice.core.dto.BookingDto;
import com.epam.training.ticketservice.core.dto.RoomDto;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.model.Booking;
import com.epam.training.ticketservice.core.model.Movie;
import com.epam.training.ticketservice.core.model.Room;
import com.epam.training.ticketservice.core.model.Screening;
import com.epam.training.ticketservice.core.model.Seat;
import com.epam.training.ticketservice.core.model.User;
import com.epam.training.ticketservice.core.repository.BookingRepository;
import com.epam.training.ticketservice.core.repository.MovieRepository;
import com.epam.training.ticketservice.core.repository.RoomRepository;
import com.epam.training.ticketservice.core.repository.ScreeningRepository;
import com.epam.training.ticketservice.core.repository.UserRepository;
import com.epam.training.ticketservice.core.service.interfaces.BookingServiceInterface;
import com.epam.training.ticketservice.core.service.interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class BookingServiceImplementation implements BookingServiceInterface {
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final UserServiceInterface userServiceInterface;
    @Override
    public String book(String movieName, String roomName, LocalDateTime screeningDate, String seats)
            throws DoesNotExists {
        Optional<Movie> movie = movieRepository.findByMovieName(movieName);
        Optional<Room> room = roomRepository.findByRoomName(roomName);
        checkMovieAndRoomExistence(movie, room);
        Optional<Screening> screening = screeningRepository.findScreeningByMovieAndRoomAndScreeningDate(movie.get(),
                room.get(), screeningDate);
        if (screening.isEmpty()) {
            throw new DoesNotExists("There's not defined a screening with this movie, room and date");
        }
        String userName = userServiceInterface.describeAccount().get().userName();
        User user = userRepository.findByUserName(userName).get();
        List<Seat> seatList = seatListSeparator(seats);
        List<Booking> bookingList = bookingRepository.findBookingByScreening(screening.get()).stream().toList();

        for (Seat seat : seatList) {
            if (!(isNumberBetween(0, seat.getChairRowSeat(), room.get().getChairRow())
                    && isNumberBetween(0, seat.getChairColumnSeat(), room.get().getChairCol()))) {
                throw new DoesNotExists("Seat " + seat + " does not exist in this room");
            }
        }

        int price = seatList.size() * 1500;
        Booking booking = new Booking(screening.get(), user, seatList, price);
        bookingRepository.save(booking);
        return returnString(seatList, price);
    }

    private void checkMovieAndRoomExistence(Optional<Movie> movie, Optional<Room> room) throws DoesNotExists {
        if (movie.isEmpty() && room.isEmpty()) {
            throw new DoesNotExists("The given movie and room do not exist");
        } else if (room.isEmpty()) {
            throw new DoesNotExists("The given room does not exist");
        } else if (movie.isEmpty()) {
            throw new DoesNotExists("The given movie does not exist");
        }
    }

    private List<Seat> seatListSeparator(String seats) {
        List<Seat> seatList = new ArrayList<>();
        for (String seatString : seats.split(" ")) {
            String[] seatLocations = seatString.split(",");
            Seat seat = new Seat(Integer.parseInt(seatLocations[0]), Integer.parseInt(seatLocations[1]));
            seatList.add(seat);
        }
        return seatList;
    }

    public static boolean isNumberBetween(int lowerBound, int number, int upperBound) {
        return number > lowerBound && number < upperBound;
    }

    private String returnString(List<Seat> seatList, int price) {
        StringBuilder seatsReturned = new StringBuilder();
        StringJoiner joiner = new StringJoiner(", ");
        for (Seat seat : seatList) {
            joiner.add(seat.toString());
        }
        return "Seats booked: " + seatsReturned.append(joiner) + "; the price for this booking is " + price + " HUF";
    }
}
