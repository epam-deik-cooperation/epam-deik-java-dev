package com.epam.training.ticketservice.core.repository;

import com.epam.training.ticketservice.core.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByRoomName(String roomName);
}
