package com.epam.training.webshop.core.cart.grossprice.impl;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.finance.money.Money;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GrossPriceCalculatorDecorator implements GrossPriceCalculator {

    private GrossPriceCalculator grossPriceCalculator;

    @Override
    public Money getAggregatedGrossPrice(Cart cart) {
        return grossPriceCalculator.getAggregatedGrossPrice(cart);
    }
}
