package com.epam.training.ticketservice.booking.price;

import com.epam.training.ticketservice.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceComponentRepository extends JpaRepository<PriceComponent, Long> {

    PriceComponent getPriceComponentByRoom_Name(String name);

    PriceComponent getPriceComponentByMovie_Title(String title);

    PriceComponent getPriceComponentByScreening(Screening screening);

}
