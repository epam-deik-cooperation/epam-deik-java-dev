package hu.unideb.inf.ticketservice.repository;

import hu.unideb.inf.ticketservice.model.Seat;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface SeatRepository extends Repository<Seat, Long> {

    List<Seat> findAll();

    void save(Seat seat);

}
