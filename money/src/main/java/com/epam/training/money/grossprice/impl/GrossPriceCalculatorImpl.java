package com.epam.training.money.grossprice.impl;

import com.epam.training.money.Money;
import com.epam.training.money.cart.Cart;
import com.epam.training.money.grossprice.GrossPriceCalculator;

public class GrossPriceCalculatorImpl implements GrossPriceCalculator {

    @Override
    public Money getAggregatedGrossPrice(Cart cart) {
        return cart.getAggregatedNetPrice();
    }
}
