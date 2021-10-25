package com.epam.training.webshop.domain;

import com.epam.training.webshop.domain.exception.NoSuchProductException;

public interface ShoppingCartService {
    void order();
    double getTotalNetPrice();
    double getTotalGrossPrice();
    void addProduct(String productName) throws NoSuchProductException;
}
