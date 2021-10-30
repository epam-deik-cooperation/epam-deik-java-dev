package com.epam.training.webshop.domain.warehouse.impl;

import com.epam.training.webshop.domain.order.model.Cart;
import com.epam.training.webshop.domain.order.model.Product;
import com.epam.training.webshop.domain.warehouse.Warehouse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DummyWarehouse implements Warehouse {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyWarehouse.class);

    @Override
    public void registerOrderedProducts(List<Product> products) {
        LOGGER.info("I have registered the order of products {}", products);
    }

    @Override
    public void notify(Cart cart) {
        registerOrderedProducts(cart.getProducts());
    }
}
