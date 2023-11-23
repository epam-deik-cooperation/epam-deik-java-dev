package com.epam.training.ticketservice.core.dto;

import com.epam.training.ticketservice.core.model.Booking;
import com.epam.training.ticketservice.core.model.Screening;
import com.epam.training.ticketservice.core.model.Seat;
import com.epam.training.ticketservice.core.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

@Getter
@RequiredArgsConstructor
public class BookingDto {
    private final ScreeningDto screeningDto;
    private final UserDto userDto;
    private final List<Seat> seatList;

    private final int price;

    public BookingDto(Booking booking) {
        screeningDto = new ScreeningDto(booking.getScreening());
        userDto = new UserDto(booking.getUser().getUserName(), booking.getUser().getRole());
        seatList = booking.getSeatList();
        price = booking.getPrice();
    }

    @Override
    public String toString() {
        StringBuilder seatsReturned = new StringBuilder();
        StringJoiner joiner = new StringJoiner(", ");
        for (Seat seat : seatList) {
            joiner.add(seat.toString());
        }
        return "Seats " + seatsReturned.append(joiner)
                + " on " + screeningDto.getMovieDto().getMovieName()
                + " in room " + screeningDto.getRoomDto().getRoomName()
                + " starting at " + screeningDto.getScreeningDate().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                + " for " + price + " HUF";
    }
}
