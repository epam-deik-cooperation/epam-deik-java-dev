package hu.unideb.inf.ticketservice.command.handler;

import hu.unideb.inf.ticketservice.command.impl.price.UpdatePriceCommand;
import hu.unideb.inf.ticketservice.command.impl.price.CreatePriceComponentCommand;
import hu.unideb.inf.ticketservice.command.impl.price.AttachPriceComponentToScreeningCommand;
import hu.unideb.inf.ticketservice.command.impl.price.AttachPriceComponentToMovieCommand;
import hu.unideb.inf.ticketservice.command.impl.price.AttachPriceComponentToRoomCommand;
import hu.unideb.inf.ticketservice.command.impl.price.ShowPriceCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class PriceCommandHandler {

    private final UpdatePriceCommand updatePriceCommand;
    private final CreatePriceComponentCommand createPriceComponentCommand;
    private final AttachPriceComponentToRoomCommand attachPriceComponentToRoomCommand;
    private final AttachPriceComponentToMovieCommand attachPriceComponentToMovieCommand;
    private final AttachPriceComponentToScreeningCommand attachPriceComponentToScreeningCommand;
    private final ShowPriceCommand showPriceCommand;

    @Autowired
    public PriceCommandHandler(UpdatePriceCommand updatePriceCommand,
                               CreatePriceComponentCommand createPriceComponentCommand,
                               AttachPriceComponentToRoomCommand attachPriceComponentToRoomCommand,
                               AttachPriceComponentToMovieCommand attachPriceComponentToMovieCommand,
                               AttachPriceComponentToScreeningCommand attachPriceComponentToScreeningCommand,
                               ShowPriceCommand showPriceCommand) {
        this.updatePriceCommand = updatePriceCommand;
        this.createPriceComponentCommand = createPriceComponentCommand;
        this.attachPriceComponentToRoomCommand = attachPriceComponentToRoomCommand;
        this.attachPriceComponentToMovieCommand = attachPriceComponentToMovieCommand;
        this.attachPriceComponentToScreeningCommand = attachPriceComponentToScreeningCommand;
        this.showPriceCommand = showPriceCommand;
    }

    @ShellMethod(value = "Updates base price", key = "update base price")
    public String createRoom(String amount) {
        return updatePriceCommand.execute(List.of(amount));
    }

    @ShellMethod(value = "Creates price component", key = "create price component")
    public String createRoom(String name, String amount) {
        return createPriceComponentCommand.execute(List.of(name,amount));
    }

    @ShellMethod(value = "Attaches price component to room", key = "attach price component to room")
    public String attachComponentToRoom(String componentName, String roomName) {
        return attachPriceComponentToRoomCommand.execute(List.of(componentName,roomName));
    }

    @ShellMethod(value = "Attaches price component to movie", key = "attach price component to movie")
    public String attachComponentToMovie(String componentName, String movieName) {
        return attachPriceComponentToMovieCommand.execute(List.of(componentName,movieName));
    }

    @ShellMethod(value = "Attaches price component to screening", key = "attach price component to screening")
    public String attachComponentToScreening(String componentName, String movieName, String roomName, String date) {
        return attachPriceComponentToScreeningCommand.execute(List.of(componentName,movieName,roomName,date));
    }

    @ShellMethod(value = "Shows price for the given screening", key = "show price for")
    public String showPriceFor(String movieName, String roomName, String date, String seats) {
        return showPriceCommand.execute(List.of(movieName,roomName,date, seats));
    }
}
