package com.epam.training.webshop.domain.grossprice;

import com.epam.training.webshop.domain.order.Cart;

public interface GrossPriceCalculator {

    double getAggregatedGrossPrice(Cart cart);
}
