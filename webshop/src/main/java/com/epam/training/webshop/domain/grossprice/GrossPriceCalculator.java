package com.epam.training.webshop.domain.grossprice;

import com.epam.training.webshop.domain.ShoppingCartService;

public interface GrossPriceCalculator {

    double getAggregatedGrossPrice(ShoppingCartService cartService);
}
