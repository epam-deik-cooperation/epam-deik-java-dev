package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.dto.RoomDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.model.User;
import com.epam.training.ticketservice.core.service.interfaces.RoomServiceInterface;
import com.epam.training.ticketservice.core.service.interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.StringJoiner;

@ShellComponent
@RequiredArgsConstructor
public class RoomCommand {
    private final RoomServiceInterface roomServiceInterface;
    private final UserServiceInterface userServiceInterface;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "Creates a room")
    public String createRoom(String roomName, int chairRow, int chairCol) {
        try {
            roomServiceInterface.roomCreate(roomName, chairRow, chairCol);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "The room created successfully";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "Updates an existing room")
    public String updateRoom(String roomName, int chairRow, int chairCol) {
        try {
            roomServiceInterface.roomUpdate(roomName, chairRow, chairCol);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "The room was updated successfully";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room", value = "Deletes an existing room")
    public String deleteRoom(String roomName) {
        try {
            roomServiceInterface.roomDelete(roomName);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "The room was deleted successfully";
    }

    @ShellMethod(key = "list rooms", value = "Lists all created rooms")
    public String listRooms() {
        List<RoomDto> roomDtoList = roomServiceInterface.roomList();
        if (!roomDtoList.isEmpty()) {
            StringBuilder roomsReturned = new StringBuilder();
            StringJoiner joiner = new StringJoiner("\n");
            for (RoomDto room : roomDtoList) {
                joiner.add(room.toString());
            }
            return  roomsReturned.append(joiner).toString();
        } else {
            return "There are no rooms at the moment";
        }
    }

    public Availability isAvailable() {
        if (userServiceInterface.describeAccount().isPresent()
                && userServiceInterface.describeAccount().get().role().equals(User.Role.ADMIN)) {
            return Availability.available();
        } else {
            return Availability.unavailable("You are not authorized");
        }
    }
}
