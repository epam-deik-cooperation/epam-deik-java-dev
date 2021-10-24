package com.epam.training.ticketservice.booking.price;

import com.epam.training.ticketservice.screening.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceComponentService {

    private final PriceComponentRepository priceComponentRepository;

    public int getPrice(Screening screening) {
        int price = 0;

        PriceComponent roomComponent = priceComponentRepository.getPriceComponentByRoom_Name(
                screening.getRoom().getName());
        PriceComponent movieComponent = priceComponentRepository.getPriceComponentByMovie_Title(
                screening.getMovie().getTitle());
        PriceComponent screeningComponent = priceComponentRepository.getPriceComponentByScreening(screening);


        if (roomComponent != null) {
            price += roomComponent.getPrice();
        }
        if (movieComponent != null) {
            price += movieComponent.getPrice();
        }
        if (screeningComponent != null) {
            price += screeningComponent.getPrice();
        }

        return price;
    }

}
