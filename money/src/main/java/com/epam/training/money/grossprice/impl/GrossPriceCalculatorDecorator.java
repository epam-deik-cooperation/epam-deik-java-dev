package com.epam.training.money.grossprice.impl;

import com.epam.training.money.Money;
import com.epam.training.money.cart.Cart;
import com.epam.training.money.grossprice.GrossPriceCalculator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
abstract class GrossPriceCalculatorDecorator implements GrossPriceCalculator {

    private final GrossPriceCalculator grossPriceCalculator;

    @Override
    public Money getAggregatedGrossPrice(Cart cart) {
        return grossPriceCalculator.getAggregatedGrossPrice(cart);
    }
}
