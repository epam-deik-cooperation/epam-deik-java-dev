package hu.unideb.inf.ticketservice.service.impl;

import hu.unideb.inf.ticketservice.model.component.Component;
import hu.unideb.inf.ticketservice.service.PriceService;

public class PriceServiceImpl implements PriceService {

    private final String currency = "HUF";
    private Integer basePrice = 1500;

    @Override
    public Integer getBasePrice() {
        return basePrice;
    }

    @Override
    public void updateBasePrice(Integer amount) {
        basePrice = amount;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public Integer calculatePrice(Component component) {
        return basePrice + component.getPrice();
    }
}
