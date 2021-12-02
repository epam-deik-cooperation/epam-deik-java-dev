package hu.unideb.inf.ticketservice.command.impl.price;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.command.PrivilegedCommand;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.PriceService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdatePriceCommand implements Command, PrivilegedCommand {

    private final PriceService priceService;
    private final LoggedInUserTrackService userTrackService;

    public UpdatePriceCommand(PriceService priceService, LoggedInUserTrackService userTrackService) {
        this.priceService = priceService;
        this.userTrackService = userTrackService;
    }

    @Override
    public String execute(List<String> parameters) {
        if (isAuthorized(userTrackService)) {
            priceService.updateBasePrice(Integer.valueOf(parameters.get(0)));
            return "Alright";
        } else {
            return "Unauthorized action!";
        }
    }
}
