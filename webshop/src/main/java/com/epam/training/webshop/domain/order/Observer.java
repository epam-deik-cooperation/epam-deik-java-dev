package com.epam.training.webshop.domain.order;

public interface Observer {

    void notify(Cart cart);
}
