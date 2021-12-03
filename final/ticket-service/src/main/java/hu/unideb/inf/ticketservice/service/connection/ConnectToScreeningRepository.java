package hu.unideb.inf.ticketservice.service.connection;

import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;

import java.util.List;

public interface ConnectToScreeningRepository {

    List<Screening> listScreenings();

    void createScreening(Screening screening);

    void deleteScreening(Screening screening);

    void updateComponent(Screening screening, PriceComponent component);

}
