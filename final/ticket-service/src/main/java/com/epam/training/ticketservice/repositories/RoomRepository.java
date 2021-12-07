package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.models.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByName(String name);
}
