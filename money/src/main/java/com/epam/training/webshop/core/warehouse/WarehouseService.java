package com.epam.training.webshop.core.warehouse;

import com.epam.training.webshop.core.checkout.model.OrderDto;

public interface WarehouseService {

    void packageOrder(OrderDto orderDto);
}
