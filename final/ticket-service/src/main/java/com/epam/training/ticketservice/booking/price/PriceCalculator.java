package com.epam.training.ticketservice.booking.price;

import com.epam.training.ticketservice.screening.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceCalculator {

    private final PriceComponentService priceComponentService;

    private static int BASE_PRICE = 1500;

    public int calculate(Screening screening, int quantityOfTickets) {
        return quantityOfTickets*(BASE_PRICE + priceComponentService.getPrice(screening));
    }


}
