package hu.unideb.inf.ticketservice.command.impl.price;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.model.component.Component;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToComponentRepository;

import java.util.List;

public class CreatePriceComponentCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackService userTrackService;
    private final ConnectToComponentRepository componentRepository;

    public CreatePriceComponentCommand(LoggedInUserTrackService userTrackService,
                                       ConnectToComponentRepository componentRepository) {
        this.userTrackService = userTrackService;
        this.componentRepository = componentRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userTrackService)) {
            Component component = new Component(parameters.get(0), Integer.valueOf(parameters.get(1)));
            componentRepository.saveComponent(component);
            return "Alright";
        } else {
            return "Unauthorized action!";
        }
    }
}
