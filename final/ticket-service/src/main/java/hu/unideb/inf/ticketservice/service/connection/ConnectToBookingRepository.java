package hu.unideb.inf.ticketservice.service.connection;

import hu.unideb.inf.ticketservice.model.Booking;

import java.util.List;

public interface ConnectToBookingRepository {

    List<Booking> getAllBookings();

    void saveBooking(Booking booking);

}
