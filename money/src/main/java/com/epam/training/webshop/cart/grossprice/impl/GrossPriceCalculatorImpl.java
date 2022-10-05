package com.epam.training.webshop.cart.grossprice.impl;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.finance.money.Money;

public class GrossPriceCalculatorImpl implements GrossPriceCalculator {

    @Override
    public Money getAggregatedGrossPrice(Cart cart) {
        return cart.getAggregatedNetPrice();
    }
}
