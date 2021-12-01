package hu.unideb.inf.ticketservice.service;

import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Seat;

import java.util.List;

public interface SeatValidationService {

    List<Seat> getSeats(String input, Room room);

    Seat isAlreadyBookedSeat(List<Seat> seats, Room room);

    Seat isSeatValid(List<Seat> seats, Room room);

}
