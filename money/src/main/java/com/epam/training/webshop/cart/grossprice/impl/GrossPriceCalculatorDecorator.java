package com.epam.training.webshop.cart.grossprice.impl;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.finance.money.Money;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GrossPriceCalculatorDecorator implements GrossPriceCalculator {

    private GrossPriceCalculator grossPriceCalculator;

    @Override
    public Money getAggregatedGrossPrice(Cart cart) {
        return grossPriceCalculator.getAggregatedGrossPrice(cart);
    }
}
