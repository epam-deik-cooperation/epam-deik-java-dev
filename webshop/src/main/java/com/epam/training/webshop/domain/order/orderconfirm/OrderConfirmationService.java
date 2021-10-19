package com.epam.training.webshop.domain.order.orderconfirm;

import com.epam.training.webshop.domain.order.Cart;
import com.epam.training.webshop.domain.order.Observer;

public interface OrderConfirmationService extends Observer {

    void sendOrderConfirmation(Cart cart);
}
