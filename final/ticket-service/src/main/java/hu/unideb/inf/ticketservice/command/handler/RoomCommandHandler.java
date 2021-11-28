package hu.unideb.inf.ticketservice.command.handler;

import hu.unideb.inf.ticketservice.command.impl.room.CreateRoomCommand;
import hu.unideb.inf.ticketservice.command.impl.room.DeleteRoomCommand;
import hu.unideb.inf.ticketservice.command.impl.room.ListRoomCommand;
import hu.unideb.inf.ticketservice.command.impl.room.UpdateRoomCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class RoomCommandHandler {

    private final CreateRoomCommand createRoomCommand;
    private final DeleteRoomCommand deleteRoomCommand;
    private final ListRoomCommand listRoomCommand;
    private final UpdateRoomCommand updateRoomCommand;

    @Autowired
    public RoomCommandHandler(CreateRoomCommand createRoomCommand, DeleteRoomCommand deleteRoomCommand,
                              ListRoomCommand listRoomCommand, UpdateRoomCommand updateRoomCommand) {
        this.createRoomCommand = createRoomCommand;
        this.deleteRoomCommand = deleteRoomCommand;
        this.listRoomCommand = listRoomCommand;
        this.updateRoomCommand = updateRoomCommand;
    }

    @ShellMethod(value = "Creates a room", key = "create room")
    public String createRoom(String name, String numberOfRows, String numberOfColumns) {
        return createRoomCommand.execute(List.of(name,numberOfRows,numberOfColumns));
    }

    @ShellMethod(value = "Deletes a room", key = "delete room")
    public String deleteRoom(String name) {
        return deleteRoomCommand.execute(List.of(name));
    }

    @ShellMethod(value = "Updates a room", key = "update room")
    public String updateRoom(String name, String numberOfRows, String numberOfColumns) {
        return updateRoomCommand.execute(List.of(name,numberOfRows,numberOfColumns));
    }

    @ShellMethod(value = "Lists rooms", key = "list rooms")
    public String listRooms() {
        return listRoomCommand.execute(null);
    }

}
