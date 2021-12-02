package hu.unideb.inf.ticketservice.command.handler;

import hu.unideb.inf.ticketservice.command.impl.price.CreatePriceComponentCommand;
import hu.unideb.inf.ticketservice.command.impl.price.UpdatePriceCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class PriceCommandHandler {

    private final UpdatePriceCommand updatePriceCommand;
    private final CreatePriceComponentCommand createPriceComponentCommand;

    public PriceCommandHandler(UpdatePriceCommand updatePriceCommand,
                               CreatePriceComponentCommand createPriceComponentCommand) {
        this.updatePriceCommand = updatePriceCommand;
        this.createPriceComponentCommand = createPriceComponentCommand;
    }

    @ShellMethod(value = "Updates base price", key = "update base price")
    public String createRoom(String amount) {
        return updatePriceCommand.execute(List.of(amount));
    }

    @ShellMethod(value = "Creates price component", key = "create price component")
    public String createRoom(String name, String amount) {
        return createPriceComponentCommand.execute(List.of(name,amount));
    }

}
