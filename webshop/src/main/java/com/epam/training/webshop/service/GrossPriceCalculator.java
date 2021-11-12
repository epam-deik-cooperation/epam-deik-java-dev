package com.epam.training.webshop.service;

import com.epam.training.webshop.service.ShoppingCartService;

public interface GrossPriceCalculator {

    double getAggregatedGrossPrice(ShoppingCartService cartService);
}
