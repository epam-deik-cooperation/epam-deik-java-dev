package com.epam.training.webshop.service.impl;

import com.epam.training.webshop.model.Cart;
import com.epam.training.webshop.model.Product;
import com.epam.training.webshop.service.WarehouseService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DummyWarehouseService implements WarehouseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyWarehouseService.class);

    @Override
    public void registerOrderedProducts(List<Product> products) {
        LOGGER.info("I have registered the order of products {}", products);
    }

    @Override
    public void notify(Cart cart) {
        registerOrderedProducts(cart.getProducts());
    }
}
