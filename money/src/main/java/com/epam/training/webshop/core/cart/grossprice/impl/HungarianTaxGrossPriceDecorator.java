package com.epam.training.webshop.core.cart.grossprice.impl;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.finance.money.Money;

public class HungarianTaxGrossPriceDecorator extends GrossPriceCalculatorDecorator {

    public HungarianTaxGrossPriceDecorator(GrossPriceCalculator grossPriceCalculator) {
        super(grossPriceCalculator);
    }

    @Override
    public Money getAggregatedGrossPrice(Cart cart) {
        return super.getAggregatedGrossPrice(cart).multiply(1.27);
    }
}
