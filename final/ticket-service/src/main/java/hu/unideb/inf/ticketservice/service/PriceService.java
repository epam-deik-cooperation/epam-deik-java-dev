package hu.unideb.inf.ticketservice.service;

import hu.unideb.inf.ticketservice.model.component.PriceComponent;

import java.util.List;

public interface PriceService {

    Integer getBasePrice();

    void updateBasePrice(Integer amount);

    String getCurrency();

    Integer calculatePrice(List<PriceComponent> components, Integer numberOfSeats);
}
