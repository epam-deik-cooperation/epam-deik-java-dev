package com.epam.training.webshop.service;

public interface GrossPriceCalculator {

    double getAggregatedGrossPrice(ShoppingCartService cartService);
}
