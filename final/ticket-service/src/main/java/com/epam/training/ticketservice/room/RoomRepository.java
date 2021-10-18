package com.epam.training.ticketservice.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


    @Modifying
    @Query("UPDATE Room r SET r.numberOfColumns = :numberOfColumns, r.numberOfRows = :numberOfRows WHERE r.name = :name")
    void update(@Param("name") String name, @Param("numberOfColumns") Integer numberOfColumns,
                @Param("numberOfRows") Integer numberOfRows);


    void deleteByName(String name);

    boolean existsByName(String name);
}
