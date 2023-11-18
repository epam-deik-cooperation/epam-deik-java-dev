package com.epam.training.ticketservice.core.service.interfaces;

public interface RoomServiceInterface {
    String roomCreate(String roomName, int chairRow, int chairCol);

    String roomUpdate(String roomName, int chairRow, int chairCol);

    String roomDelete(String roomName);

    String roomList();
}
