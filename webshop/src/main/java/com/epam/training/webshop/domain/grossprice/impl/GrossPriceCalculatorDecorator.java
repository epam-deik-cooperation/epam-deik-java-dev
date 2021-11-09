package com.epam.training.webshop.domain.grossprice.impl;

import com.epam.training.webshop.domain.ShoppingCartService;
import com.epam.training.webshop.domain.grossprice.GrossPriceCalculator;

public class GrossPriceCalculatorDecorator implements GrossPriceCalculator {

    private final GrossPriceCalculator grossPriceCalculator;

    public GrossPriceCalculatorDecorator(GrossPriceCalculator grossPriceCalculator) {
        this.grossPriceCalculator = grossPriceCalculator;
    }

    @Override
    public double getAggregatedGrossPrice(ShoppingCartService cartService) {
        return grossPriceCalculator.getAggregatedGrossPrice(cartService);
    }
}
