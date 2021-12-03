package hu.unideb.inf.ticketservice.service.connection.impl;

import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToScreeningRepository;

import java.util.List;

public class ScreeningRepositoryConnection implements ConnectToScreeningRepository {

    private final ScreeningRepository screeningRepository;

    public ScreeningRepositoryConnection(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    @Override
    public List<Screening> listScreenings() {
        return screeningRepository.findAll();
    }

    @Override
    public void createScreening(Screening screening) {
        List<Screening> screenings = listScreenings();
        if (!(screenings.contains(screening))) {
            screeningRepository.save(screening);
        }
    }

    @Override
    public void deleteScreening(Screening screening) {
        screeningRepository.delete(screening.getRoom(),screening.getMovie(),screening.getScreeningDate());
    }

    @Override
    public void updateComponent(Screening screening, PriceComponent component) {
        screeningRepository.updateComponent(screening.getRoom(), screening.getMovie(), screening.getScreeningDate(),
                component);
    }
}
