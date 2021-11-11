package com.epam.training.webshop.core.warehouse.impl;

import com.epam.training.webshop.core.checkout.CheckoutObserver;
import com.epam.training.webshop.core.checkout.model.Order;
import com.epam.training.webshop.core.warehouse.WarehouseService;
import org.springframework.stereotype.Service;

@Service
public class WarehouseServiceImpl implements WarehouseService, CheckoutObserver {

    @Override
    public void packageOrder(Order order) {
        System.out.println("WarehouseService packageOrder is called!");
    }

    @Override
    public void handleOrder(Order order) {
        packageOrder(order);
    }
}
