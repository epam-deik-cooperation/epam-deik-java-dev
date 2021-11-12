package com.epam.training.webshop.service;

import com.epam.training.webshop.model.Cart;

public interface Observer {

    void notify(Cart cart);
}
