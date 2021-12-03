package hu.unideb.inf.ticketservice.command.impl.price;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreatePriceComponentCommand implements Command, PrivilegedCommand {

    private final LoggedInUserTrackService userTrackService;
    private final ConnectToComponentRepository componentRepository;

    @Autowired
    public CreatePriceComponentCommand(LoggedInUserTrackService userTrackService,
                                       ConnectToComponentRepository componentRepository) {
        this.userTrackService = userTrackService;
        this.componentRepository = componentRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userTrackService)) {
            PriceComponent component = new PriceComponent(parameters.get(0), Integer.valueOf(parameters.get(1)));
            componentRepository.saveComponent(component);
            return "Alright";
        } else {
            return "Unauthorized action!";
        }
    }
}
