package hu.unideb.inf.ticketservice.service.connection;

import hu.unideb.inf.ticketservice.model.Screening;

import java.util.List;

public interface ConnectToScreeningRepository {

    List<Screening> listScreenings();

    void createScreening(Screening screening);

    void deleteScreening(Screening screening);

}
