package com.epam.training.webshop.repository;

import com.epam.training.webshop.domain.order.model.Cart;

public interface OrderRepository {
    void saveOrder(Cart cart);
}
