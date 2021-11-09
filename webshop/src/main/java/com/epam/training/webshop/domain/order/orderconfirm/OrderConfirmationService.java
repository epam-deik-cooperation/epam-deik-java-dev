package com.epam.training.webshop.domain.order.orderconfirm;

import com.epam.training.webshop.domain.order.Observer;
import com.epam.training.webshop.domain.order.model.Cart;

public interface OrderConfirmationService extends Observer {

    void sendOrderConfirmation(Cart cart);
}
