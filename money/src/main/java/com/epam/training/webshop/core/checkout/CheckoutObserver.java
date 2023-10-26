package com.epam.training.webshop.core.checkout;

import com.epam.training.webshop.core.checkout.model.Order;

public interface CheckoutObserver {

    void handleOrder(Order order);
}
