package com.epam.training.webshop.service;

import com.epam.training.webshop.model.Cart;

public interface OrderConfirmationService extends Observer {

    void sendOrderConfirmation(Cart cart);
}
