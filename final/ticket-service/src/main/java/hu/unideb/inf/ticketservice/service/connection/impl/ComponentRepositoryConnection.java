package hu.unideb.inf.ticketservice.service.connection.impl;

import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.repository.ComponentRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToComponentRepository;

import java.util.List;

public class ComponentRepositoryConnection implements ConnectToComponentRepository {

    private final ComponentRepository componentRepository;

    public ComponentRepositoryConnection(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    public List<PriceComponent> getComponents() {
        return componentRepository.findAll();
    }

    @Override
    public void saveComponent(PriceComponent component) {
        componentRepository.save(component);
    }
}
