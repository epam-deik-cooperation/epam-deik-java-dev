package hu.unideb.inf.ticketservice.repository;

import hu.unideb.inf.ticketservice.model.Booking;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface BookingRepository extends Repository<Booking, Long> {

    List<Booking> findAll();

    void save(Booking booking);

}
