package hu.unideb.inf.ticketservice.command;

import java.util.List;

public interface Command {

    String execute(List<String> parameters);

}
