package com.epam.training.ticketservice.core.service.interfaces;

import com.epam.training.ticketservice.core.dto.RoomDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;

import java.util.List;

public interface RoomServiceInterface {
    void roomCreate(String roomName, int chairRow, int chairCol) throws AlreadyExists;

    void roomUpdate(String roomName, int chairRow, int chairCol) throws DoesNotExists;

    void roomDelete(String roomName) throws DoesNotExists;

    List<RoomDto> roomList();
}
