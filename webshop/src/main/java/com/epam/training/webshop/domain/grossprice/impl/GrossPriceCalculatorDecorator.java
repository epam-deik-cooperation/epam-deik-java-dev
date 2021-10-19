package com.epam.training.webshop.domain.grossprice.impl;

import com.epam.training.webshop.domain.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.domain.order.Cart;

public class GrossPriceCalculatorDecorator implements GrossPriceCalculator {

    private final GrossPriceCalculator grossPriceCalculator;

    public GrossPriceCalculatorDecorator(GrossPriceCalculator grossPriceCalculator) {
        this.grossPriceCalculator = grossPriceCalculator;
    }

    @Override
    public double getAggregatedGrossPrice(Cart cart) {
        return grossPriceCalculator.getAggregatedGrossPrice(cart);
    }
}