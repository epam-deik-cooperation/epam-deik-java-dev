package com.epam.training.webshop.domain.order.orderconfirm.impl;

import com.epam.training.webshop.domain.order.Cart;
import com.epam.training.webshop.domain.order.orderconfirm.OrderConfirmationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyOrderConfirmationService implements OrderConfirmationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyOrderConfirmationService.class);

    @Override
    public void sendOrderConfirmation(Cart cart) {
        LOGGER.info("An order confirmation for cart {} had been sent.", cart.toString());
    }

    @Override
    public void notify(Cart cart) {
        sendOrderConfirmation(cart);
    }
}
