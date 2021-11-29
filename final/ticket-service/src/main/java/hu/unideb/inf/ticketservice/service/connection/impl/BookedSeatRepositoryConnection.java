package hu.unideb.inf.ticketservice.service.connection.impl;

import hu.unideb.inf.ticketservice.model.Seat;
import hu.unideb.inf.ticketservice.repository.BookedSeatRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToBookedSeatRepository;

import java.util.List;

public class BookedSeatRepositoryConnection implements ConnectToBookedSeatRepository {

    private final BookedSeatRepository bookedSeatRepository;

    public BookedSeatRepositoryConnection(BookedSeatRepository bookedSeatRepository) {
        this.bookedSeatRepository = bookedSeatRepository;
    }

    @Override
    public List<Seat> getAllSeats() {
        return bookedSeatRepository.findAll();
    }

    @Override
    public void saveSeat(Seat seat) {
        bookedSeatRepository.save(seat);
    }
}
