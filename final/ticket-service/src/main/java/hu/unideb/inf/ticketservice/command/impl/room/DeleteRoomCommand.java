package hu.unideb.inf.ticketservice.command.impl.room;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRoomRepository;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteRoomCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackImpl userService;
    private final ConnectToRoomRepository roomRepository;


    @Autowired
    public DeleteRoomCommand(LoggedInUserTrackImpl userService, ConnectToRoomRepository roomRepository) {
        this.userService = userService;
        this.roomRepository = roomRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userService)) {
            roomRepository.deleteRoom(parameters.get(0));
            return "Alright";
        } else {
            return "Unauthorized action!";
        }
    }
}
