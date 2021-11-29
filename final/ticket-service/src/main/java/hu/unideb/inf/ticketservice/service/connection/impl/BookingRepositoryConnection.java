package hu.unideb.inf.ticketservice.service.connection.impl;

import hu.unideb.inf.ticketservice.model.Booking;
import hu.unideb.inf.ticketservice.model.Seat;
import hu.unideb.inf.ticketservice.repository.BookingRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToBookedSeatRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToBookingRepository;

import java.util.List;

public class BookingRepositoryConnection implements ConnectToBookingRepository {

    private final BookingRepository bookingRepository;

    public BookingRepositoryConnection(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }
}
