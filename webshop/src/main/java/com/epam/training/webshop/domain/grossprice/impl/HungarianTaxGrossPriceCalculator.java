package com.epam.training.webshop.domain.grossprice.impl;

import com.epam.training.webshop.domain.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.domain.order.Cart;

public class HungarianTaxGrossPriceCalculator extends GrossPriceCalculatorDecorator {

    private static final double HUNGARIAN_TAX_RATE = 1.27D;

    public HungarianTaxGrossPriceCalculator(GrossPriceCalculator grossPriceCalculator) {
        super(grossPriceCalculator);
    }

    @Override
    public double getAggregatedGrossPrice(Cart cart) {
        return super.getAggregatedGrossPrice(cart) * HUNGARIAN_TAX_RATE;
    }
}
