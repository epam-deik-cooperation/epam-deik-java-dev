package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.businesslogic.account.AccountService;
import com.epam.training.ticketservice.businesslogic.room.RoomService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class RoomCommands {

    RoomService roomService;
    AccountService accountService;

    private Availability isAdmin() {
        return Authentication.admin(accountService.getCurrentUser());
    }

    public RoomCommands(RoomService roomService, AccountService accountService) {
        this.roomService = roomService;
        this.accountService = accountService;
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "create room", value = "Create a new room.")
    public void createRoom(String name, int rows, int columns) {
        roomService.createRoom(name, rows, columns);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "update room", value = "Update an existing room.")
    public void updateRoom(String name, int rows, int columns) {
        roomService.updateRoom(name, rows, columns);
    }

    @ShellMethodAvailability("isAdmin")
    @ShellMethod(key = "delete room", value = "Delete an existing room.")
    public void deleteRoom(String name) {
        roomService.deleteRoom(name);
    }

    @ShellMethod(key = "list rooms", value = "List existing rooms.")
    public String listRooms() {
        var rooms = roomService.listRooms();
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (var r : rooms) {
            stringBuilder.append("Room ")
                    .append(r.getName())
                    .append(" with ")
                    .append(r.getRows() * r.getColumns())
                    .append(" seats, ")
                    .append(r.getRows())
                    .append(" rows and ")
                    .append(r.getColumns())
                    .append(" columns")
                    .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
