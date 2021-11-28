package hu.unideb.inf.ticketservice.command.impl.room;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.util.List;

@Component
public class ListRoomCommand implements Command {

    private final ConnectToRepositoriesService repositoriesService;

    @Autowired
    public ListRoomCommand(ConnectToRepositoriesService repositoriesService) {
        this.repositoriesService = repositoriesService;
    }

    @Override
    public String execute(@Null List<String> parameters) {
        if (repositoriesService.listRooms().isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            List<Room> rooms = repositoriesService.listRooms();
            StringBuilder result = new StringBuilder();
            for (int i = 0;i < rooms.size();i++) {
                if (i + 1 == rooms.size()) {
                    result.append(rooms.get(i));
                } else {
                    result.append(rooms.get(i));
                    result.append("\n");
                }
            }
            return result.toString();
        }
    }
}
