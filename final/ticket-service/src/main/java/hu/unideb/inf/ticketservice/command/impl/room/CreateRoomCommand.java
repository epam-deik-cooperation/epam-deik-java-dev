package hu.unideb.inf.ticketservice.command.impl.room;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateRoomCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackImpl userService;
    private final ConnectToRepositoriesService repositoriesService;

    @Autowired
    public CreateRoomCommand(LoggedInUserTrackImpl userService, ConnectToRepositoriesService repositoriesService) {
        this.userService = userService;
        this.repositoriesService = repositoriesService;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userService)) {
            Room room = new Room(parameters.get(0), Integer.valueOf(parameters.get(1)),
                    Integer.valueOf(parameters.get(2)));
            repositoriesService.createRoom(room);
            return "Alright";
        } else {
            return "Unauthorized action!";
        }
    }
}
