package com.epam.training.webshop.core.warehouse;

import com.epam.training.webshop.core.checkout.model.Order;

public interface WarehouseService {

    void packageOrder(Order order);
}
