package hu.unideb.inf.ticketservice.command.impl.screening;

import hu.unideb.inf.ticketservice.command.Command;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.util.List;

@Component
public class ListScreeningCommand implements Command {

    private final ConnectToRepositoriesService repositoriesService;

    @Autowired
    public ListScreeningCommand(ConnectToRepositoriesService repositoriesService) {
        this.repositoriesService = repositoriesService;
    }

    @Override
    public String execute(@Null List<String> parameters) {
        if (repositoriesService.listScreenings().isEmpty()) {
            return "There are no screenings";
        } else {
            List<Screening> screenings = repositoriesService.listScreenings();
            StringBuilder result = new StringBuilder();
            for (int i = 0;i < screenings.size();i++) {
                if (i + 1 == screenings.size()) {
                    result.append(screenings.get(i));
                } else {
                    result.append(screenings.get(i));
                    result.append("\n");
                }
            }
            return result.toString();
        }
    }
}
