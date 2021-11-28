package hu.unideb.inf.ticketservice.repository.init;

import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

//Uncomment the line below to initialize repository
//@Repository
public class RoomRepositoryInitializer {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomRepositoryInitializer(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    private final List<Room> initialRooms = List.of(
            new Room("Room1",10,10),
            new Room("Room2",10,10),
            new Room("Room3",10,20)
    );

    @PostConstruct
    private void saveRooms() {
        initialRooms.forEach(roomRepository::save);
    }
}
