package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class RoomCommand {

    private final RoomService roomService;
    private final UserService userService;

    @ShellMethod(key = "list rooms", value = "List all rooms")
    public String getRoomList() {
        List<RoomDto> rooms = roomService.getRoomList();
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            StringBuilder result = new StringBuilder();
            rooms.forEach(room -> {
                result.append(room.toString() + "\n");
            });
            return result.toString().trim();
        }
    }

    @ShellMethod(key = "create room", value = "Creates a room")
    @ShellMethodAvailability("isAvailable")
    public String createRoom(String name, int rows, int columns) {
        Optional<RoomDto> room = roomService.getRoomByName(name);
        if (room.isPresent()) {
            return "Room " + name + " already exists, try \"update room\" instead";
        } else {
            RoomDto newRoom = RoomDto.builder().name(name).rows(rows).columns(columns).build();
            roomService.createRoom(newRoom);
            return "Room " + name + " created successfully";
        }
    }

    @ShellMethod(key = "delete room", value = "Deletes a movie if exists")
    @ShellMethodAvailability("isAvailable")
    public String deleteRoom(String name) {
        Optional<RoomDto> room = roomService.getRoomByName(name);
        if (room.isEmpty()) {
            return "Room " + name + " doesn't exist";
        } else {
            if (roomService.deleteRoom(room.get())) {
                return "Room " + name + " deleted successfully";
            }
            return "Deleting " + name + " failed";
        }
    }

    @ShellMethod(key = "update room", value = "Updates an existing movie")
    @ShellMethodAvailability("isAvailable")
    public String updateRoom(String name, int rows, int columns) {
        Optional<RoomDto> room = roomService.getRoomByName(name);
        if (room.isEmpty()) {
            return "Room " + name + " doesn't exist";
        } else {
            roomService.deleteRoom(room.get());
            RoomDto newRoom = RoomDto.builder().name(name).rows(rows).columns(columns).build();
            roomService.createRoom(newRoom);
            return "Room " + name + " updated successfully";
        }
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        if (user.isPresent() && user.get().getRole() == User.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You are not an admin!");
    }
}
