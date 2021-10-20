package com.epam.training.ticketservice.room;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class RoomCommands {

    private final RoomService roomService;

    @ShellMethod(value = "format: create room name rows columns", key = "create room")
    public String create(String name, int rows, int columns) {

        roomService.createRoom(Room.builder()
                .name(name)
                .numberOfRows(rows)
                .numberOfColumns(columns)
                .build());

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

    public String list() {
        /*
            TODO:
                - 'list rooms' command implementation
         */

        return "";
    }


}
