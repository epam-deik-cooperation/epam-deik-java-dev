package hu.unideb.inf.ticketservice.command.impl.room;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteRoomCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackImpl userService;
    private final ConnectToRepositoriesService repositoriesService;


    @Autowired
    public DeleteRoomCommand(LoggedInUserTrackImpl userService, ConnectToRepositoriesService repositoriesService) {
        this.userService = userService;
        this.repositoriesService = repositoriesService;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userService)) {
            repositoriesService.deleteRoom(parameters.get(0));
            return "Alright";
        } else {
            return "Unauthorized action!";
        }
    }
}
