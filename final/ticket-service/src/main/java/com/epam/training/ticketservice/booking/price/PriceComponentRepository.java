package com.epam.training.ticketservice.booking.price;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PriceComponentRepository extends JpaRepository<PriceComponent, Long> {

    PriceComponent getByNameContainingIgnoreCase(String name);

    boolean existsByNameContainingIgnoreCase(String name);


}
