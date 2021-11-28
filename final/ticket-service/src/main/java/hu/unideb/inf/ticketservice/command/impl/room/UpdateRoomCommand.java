package hu.unideb.inf.ticketservice.command.impl.room;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateRoomCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackService userService;
    private final ConnectToRepositoriesService repositoriesService;

    @Autowired
    public UpdateRoomCommand(LoggedInUserTrackService userService, ConnectToRepositoriesService repositoriesService) {
        this.userService = userService;
        this.repositoriesService = repositoriesService;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userService)) {
            String roomName = parameters.get(0);
            List<Room> roomList = repositoriesService.listRooms();
            Room toBeUpdated = findRoomByName(roomName,roomList);
            if (toBeUpdated == null) {
                return "No such room like " + roomName;
            } else {
                Room room = new Room(parameters.get(0), Integer.valueOf(parameters.get(1)),
                        Integer.valueOf(parameters.get(2)));
                repositoriesService.updateRoom(room.getName(), room);
                return "Alright";
            }
        } else {
            return "Unauthorized action!";
        }
    }

    private Room findRoomByName(String name, List<Room> roomList) {
        return roomList.stream()
                .filter(m -> m.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

}
