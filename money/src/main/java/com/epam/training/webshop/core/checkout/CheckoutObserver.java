package com.epam.training.webshop.core.checkout;

import com.epam.training.webshop.core.checkout.model.OrderDto;

public interface CheckoutObserver {

    void handleOrder(OrderDto orderDto);
}
