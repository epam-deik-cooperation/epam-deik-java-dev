package com.epam.training.webshop.core.warehouse.impl;

import com.epam.training.webshop.core.checkout.CheckoutObserver;
import com.epam.training.webshop.core.checkout.model.OrderDto;
import com.epam.training.webshop.core.warehouse.WarehouseService;
import org.springframework.stereotype.Service;

@Service
public class WarehouseServiceImpl implements WarehouseService, CheckoutObserver {

    @Override
    public void packageOrder(OrderDto orderDto) {
        System.out.println("WarehouseService packageOrder is called!");
    }

    @Override
    public void handleOrder(OrderDto orderDto) {
        packageOrder(orderDto);
    }
}
