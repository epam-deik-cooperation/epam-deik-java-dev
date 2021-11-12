package com.epam.training.webshop.service.impl;

import com.epam.training.webshop.model.Cart;
import com.epam.training.webshop.service.OrderConfirmationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DummyOrderConfirmationService implements OrderConfirmationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyOrderConfirmationService.class);

    @Override
    public void sendOrderConfirmation(Cart cart) {
        LOGGER.info("An order confirmation for cart {} had been sent.", cart);
    }

    @Override
    public void notify(Cart cart) {
        sendOrderConfirmation(cart);
    }
}
