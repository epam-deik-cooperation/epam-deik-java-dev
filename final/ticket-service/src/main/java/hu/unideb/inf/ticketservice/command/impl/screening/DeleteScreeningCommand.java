package hu.unideb.inf.ticketservice.command.impl.screening;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteScreeningCommand implements Command, PrivilegedCommand {

    private final ConnectToScreeningRepository screeningRepository;
    private final LoggedInUserTrackService userService;

    @Autowired
    public DeleteScreeningCommand(ConnectToScreeningRepository screeningRepository,
                                  LoggedInUserTrackService userService) {
        this.screeningRepository = screeningRepository;
        this.userService = userService;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userService)) {
            String movieName = parameters.get(0);
            String roomName = parameters.get(1);
            String date = parameters.get(2);
            List<Screening> screenings = screeningRepository.listScreenings();
            Screening screening = findScreening(movieName,roomName,date,screenings);
            if (screening != null) {
                screeningRepository.deleteScreening(screening);
                return "Alright";
            } else {
                return "There is no screening with movie " + movieName + " inside room " + roomName + " at " + date;
            }
        } else {
            return "Unauthorized action!";
        }
    }

    private Screening findScreening(String movieName, String roomName, String date, List<Screening> screenings) {
        return screenings.stream()
                .filter(e ->
                    e.getMovie().getName().equals(movieName)
                            && e.getRoom().getName().equals(roomName)
                            && e.getScreeningDate().equals(date))
                .findFirst()
                .orElse(null);
    }

}
