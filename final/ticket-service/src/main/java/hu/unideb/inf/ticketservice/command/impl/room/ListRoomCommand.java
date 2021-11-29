package hu.unideb.inf.ticketservice.command.impl.room;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.util.List;

@Component
public class ListRoomCommand implements Command {

    private final ConnectToRoomRepository roomRepository;

    @Autowired
    public ListRoomCommand(ConnectToRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public String execute(@Null List<String> parameters) {
        if (roomRepository.listRooms().isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            List<Room> rooms = roomRepository.listRooms();
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
