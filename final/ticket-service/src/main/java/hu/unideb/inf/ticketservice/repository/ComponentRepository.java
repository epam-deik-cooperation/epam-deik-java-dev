package hu.unideb.inf.ticketservice.repository;

import hu.unideb.inf.ticketservice.model.component.Component;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ComponentRepository extends Repository<Component, Long> {

    List<Component> findAll();

    void save(Component component);

}
