package hu.unideb.inf.ticketservice.service.connection;

import hu.unideb.inf.ticketservice.model.component.Component;

import java.util.List;

public interface ConnectToComponentRepository {

    List<Component> getComponents();

    void saveComponent(Component component);

}
