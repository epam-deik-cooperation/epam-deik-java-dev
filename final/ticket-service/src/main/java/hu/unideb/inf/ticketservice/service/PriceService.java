package hu.unideb.inf.ticketservice.service;

import hu.unideb.inf.ticketservice.model.component.Component;

public interface PriceService {

    Integer getBasePrice();

    void updateBasePrice(Integer amount);

    String getCurrency();

    Integer calculatePrice(Component component);
}
