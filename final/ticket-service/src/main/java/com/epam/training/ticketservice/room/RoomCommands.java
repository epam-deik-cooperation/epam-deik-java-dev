package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.SecuredCommands;
import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class RoomCommands extends SecuredCommands {

    private final RoomService roomService;

    @ShellMethod(value = "create room name rows columns", key = "create room")
    @ShellMethodAvailability("isAccountAdmin")
    public String createRoom(String name, int rows, int columns) {
        try {
            roomService.createRoom(Room.builder()
                    .name(name)
                    .numberOfRows(rows)
                    .numberOfColumns(columns)
                    .build());
        } catch (AlreadyExistsException e) {
            return e.getMessage();
        }
        return String.format("Successfully created room '%s'", name);
    }

    @ShellMethod(value = "update room roomName rows cols", key = "update room")
    @ShellMethodAvailability("isAccountAdmin")
    public String updateRoom(String roomName, int rows, int cols) {
        try {
            roomService.updateRoom(Room.builder()
                    .name(roomName)
                    .numberOfRows(rows)
                    .numberOfColumns(cols)
                    .build());
        } catch (NotFoundException e) {
            return e.getMessage();
        }
        return String.format("Successfully updated room '%s'", roomName);
    }

    @ShellMethod(value = "delete room roomName", key = "delete room")
    @ShellMethodAvailability("isAccountAdmin")
    public String deleteRoom(String roomName) {
        try {
            roomService.deleteRoom(roomName);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
        return String.format("Successfully deleted room '%s' ", roomName);
    }

    @ShellMethod(value = "list rooms", key = "list rooms")
    public String listRooms() {

        StringBuilder sb = new StringBuilder();

        if (!roomService.getAllRooms().isEmpty()) {
            roomService.getAllRooms().forEach(x -> sb.append(x).append("\n"));
            sb.setLength(sb.length() - 1);
            return sb.toString();
        } else {
            return "There are no rooms at the moment";
        }
    }


}
