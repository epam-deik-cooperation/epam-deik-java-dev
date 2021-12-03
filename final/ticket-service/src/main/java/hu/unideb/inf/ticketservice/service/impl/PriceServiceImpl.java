package hu.unideb.inf.ticketservice.service.impl;

import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.service.PriceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public Integer calculatePrice(List<PriceComponent> components, Integer numberOfSeats) {
        Integer sumOfComponents = 0;
        for (PriceComponent component : components) {
            sumOfComponents += component.getPrice();
        }
        return sumOfComponents + (numberOfSeats * basePrice);
    }
}
