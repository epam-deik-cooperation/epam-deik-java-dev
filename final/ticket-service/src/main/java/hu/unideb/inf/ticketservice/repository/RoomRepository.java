package hu.unideb.inf.ticketservice.repository;

import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RoomRepository extends Repository<Room,Long> {
    List<Room> findAll();

    void save(Room room);

    void deleteByName(String name);

    @Modifying
    @Query("update Room e set e.numberOfSeats = ?1, e.numberOfRows = ?2, "
            + "e.numberOfColumns = ?3, e.name = ?4 where e.name = ?4")
    void updateByName(Integer numberOfSeats, Integer numberOfRows, Integer numberOfColumns, String name);

    @Modifying
    @Query("update Room e set e.component = ?2 where e.name = ?1")
    void updateComponent(String name, PriceComponent component);
}
