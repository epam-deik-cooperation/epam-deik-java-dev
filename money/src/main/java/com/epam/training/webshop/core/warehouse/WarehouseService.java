package com.epam.training.webshop.core.warehouse;

import com.epam.training.webshop.core.checkout.CheckoutObserver;
import com.epam.training.webshop.core.checkout.model.OrderDto;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService implements CheckoutObserver {

    @Override
    public void handleOrder(OrderDto orderDto) {
        System.out.println("WarehouseService handleOrder method is called!");
    }
}
