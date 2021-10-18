package com.epam.training.webshop.domain.grossprice.impl;

import com.epam.training.webshop.domain.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.domain.order.Cart;

public class GrossPriceCalculatorImpl implements GrossPriceCalculator {

    @Override
    public double getAggregatedGrossPrice(Cart cart) {
        return cart.getTotalNetPrice();
    }
}
