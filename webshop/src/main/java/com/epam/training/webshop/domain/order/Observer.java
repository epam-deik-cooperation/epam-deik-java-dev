package com.epam.training.webshop.domain.order;

import com.epam.training.webshop.domain.order.model.Cart;

public interface Observer {

    void notify(Cart cart);
}
