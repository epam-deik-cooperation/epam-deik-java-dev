package hu.unideb.inf.ticketservice.command.impl.price;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToComponentRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttachPriceComponentToRoomCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackService userTrackService;
    private final ConnectToComponentRepository componentRepository;
    private final ConnectToRoomRepository roomRepository;

    @Autowired
    public AttachPriceComponentToRoomCommand(LoggedInUserTrackService userTrackService,
                                             ConnectToComponentRepository componentRepository,
                                             ConnectToRoomRepository roomRepository) {
        this.userTrackService = userTrackService;
        this.componentRepository = componentRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userTrackService)) {
            String componentName = parameters.get(0);
            List<PriceComponent> priceComponentList = componentRepository.getComponents();
            PriceComponent actualComponent = findComponentByName(componentName, priceComponentList);
            if (actualComponent != null) {
                String roomName = parameters.get(1);
                List<Room> roomList = roomRepository.listRooms();
                Room actualRoom = findRoomByName(roomName,roomList);
                if (actualRoom != null) {
                    roomRepository.updateComponent(actualRoom.getName(), actualComponent);
                    actualRoom.setComponent(actualComponent);
                    return "Alright";
                } else {
                    return "There is no room with name " + roomName;
                }
            } else {
                return "There is no component with name " + componentName;
            }
        } else {
            return "Unauthorized action!";
        }
    }

    private Room findRoomByName(String roomName, List<Room> roomList) {
        return roomList.stream()
                .filter(c -> c.getName().equals(roomName))
                .findFirst()
                .orElse(null);
    }

    private PriceComponent findComponentByName(String componentName, List<PriceComponent> priceComponentList) {
        return priceComponentList.stream()
                .filter(c -> c.getName().equals(componentName))
                .findFirst()
                .orElse(null);
    }
}
