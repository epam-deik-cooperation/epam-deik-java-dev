package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.service.implementations.RoomServiceImplementation;
import com.epam.training.ticketservice.core.service.implementations.UserServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class RoomCommand {
    private final RoomServiceImplementation roomServiceImplementation;
    private final UserServiceImplementation userServiceImplementation;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "Creates a room")
    public String createRoom(String roomName, int chairRow, int chairCol){
        return roomServiceImplementation.roomCreate(roomName, chairRow, chairCol);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "Updates an existing room")
    public String updateRoom(String roomName, int chairRow, int chairCol){
        return roomServiceImplementation.roomUpdate(roomName, chairRow, chairCol);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room", value = "Deletes an existing room")
    public String deleteRoom(String roomName){
        return roomServiceImplementation.roomDelete(roomName);
    }

    @ShellMethod(key = "list rooms", value = "Lists all created rooms")
    public String listRooms(){
        return roomServiceImplementation.roomList();
    }

    public Availability isAvailable(){
        if (userServiceImplementation.getLoggedInUser() != null){
            return Availability.available();
        }
        else {
            return Availability.unavailable("You are not authorized");
        }
    }
}
