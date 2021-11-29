package hu.unideb.inf.ticketservice.command.impl.room;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRoomRepository;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateRoomCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackImpl userService;
    private final ConnectToRoomRepository roomRepository;

    @Autowired
    public CreateRoomCommand(LoggedInUserTrackImpl userService, ConnectToRoomRepository roomRepository) {
        this.userService = userService;
        this.roomRepository = roomRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userService)) {
            Room room = new Room(parameters.get(0), Integer.valueOf(parameters.get(1)),
                    Integer.valueOf(parameters.get(2)));
            roomRepository.createRoom(room);
            return "Alright";
        } else {
            return "Unauthorized action!";
        }
    }
}
