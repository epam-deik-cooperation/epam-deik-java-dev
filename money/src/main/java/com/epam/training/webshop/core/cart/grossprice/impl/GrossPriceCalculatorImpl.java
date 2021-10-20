package com.epam.training.webshop.core.cart.grossprice.impl;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.finance.money.Money;
import java.util.Objects;

public class GrossPriceCalculatorImpl implements GrossPriceCalculator {

    @Override
    public Money getAggregatedGrossPrice(Cart cart) {
        Objects.requireNonNull(cart, "Cart cannot be null");
        return cart.getAggregatedNetPrice();
    }
}
