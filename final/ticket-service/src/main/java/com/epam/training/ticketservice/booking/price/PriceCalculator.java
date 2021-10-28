package com.epam.training.ticketservice.booking.price;

import com.epam.training.ticketservice.screening.Screening;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceCalculator {

    private final PriceComponentService priceComponentService;

    @Setter
    @Getter
    private static int basePrice = 1500;

    public int calculate(Screening screening, int quantityOfTickets) {
        return quantityOfTickets * (basePrice + priceComponentService.getPrice(screening));
    }


}
