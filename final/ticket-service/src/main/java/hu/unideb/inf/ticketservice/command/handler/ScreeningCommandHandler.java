package hu.unideb.inf.ticketservice.command.handler;

import hu.unideb.inf.ticketservice.command.impl.screening.CreateScreeningCommand;
import hu.unideb.inf.ticketservice.command.impl.screening.DeleteScreeningCommand;
import hu.unideb.inf.ticketservice.command.impl.screening.ListScreeningCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class ScreeningCommandHandler {

    private final CreateScreeningCommand createScreeningCommand;
    private final DeleteScreeningCommand deleteScreeningCommand;
    private final ListScreeningCommand listScreeningCommand;

    @Autowired
    public ScreeningCommandHandler(CreateScreeningCommand createScreeningCommand,
                                   DeleteScreeningCommand deleteScreeningCommand,
                                   ListScreeningCommand listScreeningCommand) {
        this.createScreeningCommand = createScreeningCommand;
        this.deleteScreeningCommand = deleteScreeningCommand;
        this.listScreeningCommand = listScreeningCommand;
    }

    @ShellMethod(value = "Creates a screening in the given room of the given movie",key = "create screening")
    public String createScreening(String movieName, String roomName, String date) {
        return createScreeningCommand.execute(List.of(movieName,roomName,date));
    }

    @ShellMethod(value = "Deletes a screening",key = "delete screening")
    public String deleteScreening(String movieName, String roomName, String date) {
        return deleteScreeningCommand.execute(List.of(movieName,roomName,date));
    }

    @ShellMethod(value = "Lists screenings",key = "list screenings")
    public String listScreening() {
        return listScreeningCommand.execute(null);
    }


}
