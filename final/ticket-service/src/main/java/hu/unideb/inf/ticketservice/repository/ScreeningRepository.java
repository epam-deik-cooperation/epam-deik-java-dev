package hu.unideb.inf.ticketservice.repository;

import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ScreeningRepository extends Repository<Screening,Long> {
    List<Screening> findAll();

    void save(Screening screening);

    @Transactional
    @Modifying
    @Query("delete from Screening e where e.room = ?1 and e.movie = ?2 and e.screeningDate = ?3")
    void delete(Room roomId, Movie movieId, String date);
}
