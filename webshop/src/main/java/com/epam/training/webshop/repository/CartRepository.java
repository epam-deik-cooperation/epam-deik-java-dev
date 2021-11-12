package com.epam.training.webshop.repository;

import org.springframework.data.repository.Repository;

import com.epam.training.webshop.model.Cart;

public interface CartRepository extends Repository<Cart, Long> {
    void save(Cart cart);
}
