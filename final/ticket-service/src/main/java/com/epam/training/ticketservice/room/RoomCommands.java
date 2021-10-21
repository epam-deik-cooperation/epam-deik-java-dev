package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.exception.AlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class RoomCommands {

    private final RoomService roomService;

    @ShellMethod(value = "format: create room name rows columns", key = "create room")
    public String create(String name, int rows, int columns) {

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

    public String update(String roomName, int rows, int cols) {
        /*
            TODO:
                - 'update room' command implementation
         */

        return "";
    }

    public String delete(String roomName) {
        /*
            TODO:
                - 'delete room' command implementation
         */
        return "";
    }

    @ShellMethod(value = "list movies", key = "list rooms")
    public void list() {
        if (!roomService.getAllRooms().isEmpty()) {
            roomService.getAllRooms().forEach(System.out::println);
        } else {
            System.out.println("There are no rooms at the moment");
        }

    }


}
