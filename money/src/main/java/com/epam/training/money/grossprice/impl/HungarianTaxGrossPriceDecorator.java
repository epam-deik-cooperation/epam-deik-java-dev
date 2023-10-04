package com.epam.training.money.grossprice.impl;

import com.epam.training.money.Money;
import com.epam.training.money.cart.Cart;
import com.epam.training.money.grossprice.GrossPriceCalculator;

public class HungarianTaxGrossPriceDecorator extends GrossPriceCalculatorDecorator {

    public HungarianTaxGrossPriceDecorator(GrossPriceCalculator grossPriceCalculator) {
        super(grossPriceCalculator);
    }

    @Override
    public Money getAggregatedGrossPrice(Cart cart) {
        return super.getAggregatedGrossPrice(cart).multiply(1.27);
    }
}
