package hu.unideb.inf.ticketservice.repository;

import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ComponentRepository extends Repository<PriceComponent, Long> {

    List<PriceComponent> findAll();

    void save(PriceComponent component);

}
