package hu.unideb.inf.ticketservice.service.connection;

import hu.unideb.inf.ticketservice.model.component.PriceComponent;

import java.util.List;

public interface ConnectToComponentRepository {

    List<PriceComponent> getComponents();

    void saveComponent(PriceComponent component);

}
