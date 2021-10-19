package com.epam.training.webshop.domain.order.orderconfirm.lib.impl;

import com.epam.training.webshop.domain.order.model.Product;
import com.epam.training.webshop.domain.order.orderconfirm.lib.ConfirmationService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailConfirmationService implements ConfirmationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConfirmationService.class);

    @Override
    public void sendConfirmationMessageAbout(List<Product> products) {
        LOGGER.info("Sending an e-mail confirmation about {} products", products);
    }
}
