package com.epam.training.ticketservice.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Room r " +
            "SET r.numberOfColumns = :numberOfColumns, r.numberOfRows = :numberOfRows " +
            "WHERE lower(r.name) = lower(:name)")
    void update(@Param("name") String name, @Param("numberOfColumns") Integer numberOfColumns,
                @Param("numberOfRows") Integer numberOfRows);

    @Transactional
    void deleteByNameContainingIgnoreCase(String name);

    boolean existsByNameContainingIgnoreCase(String name);

    Room findByNameContainingIgnoreCase(String name);
}
