package hu.unideb.inf.ticketservice.service.connection;

import hu.unideb.inf.ticketservice.model.Seat;

import java.util.List;

public interface ConnectToBookedSeatRepository {

    List<Seat> getAllSeats();

    void saveSeat(Seat seat);

}
