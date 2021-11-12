package com.epam.training.webshop.lib.impl;

import com.epam.training.webshop.lib.ConfirmationService;
import com.epam.training.webshop.model.Product;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailConfirmationService implements ConfirmationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConfirmationService.class);

    @Override
    public void sendConfirmationMessageAbout(List<Product> products) {
        LOGGER.info("Sending an e-mail confirmation about {} products", products);
    }
}
