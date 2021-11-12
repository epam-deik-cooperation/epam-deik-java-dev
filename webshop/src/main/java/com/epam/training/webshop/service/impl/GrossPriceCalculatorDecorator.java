package com.epam.training.webshop.service.impl;

import com.epam.training.webshop.service.ShoppingCartService;
import com.epam.training.webshop.service.GrossPriceCalculator;

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
