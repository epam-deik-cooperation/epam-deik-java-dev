package com.epam.training.webshop.repository.impl;

import com.epam.training.webshop.domain.order.model.Cart;
import com.epam.training.webshop.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DummyOrderRepository implements OrderRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyOrderRepository.class);

    @Override
    public void saveOrder(Cart cart) {
        LOGGER.info("Saving order {}", cart);
    }
}
