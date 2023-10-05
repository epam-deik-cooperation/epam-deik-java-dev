package com.epam.training.money.grossprice;

import com.epam.training.money.Money;
import com.epam.training.money.cart.Cart;

public interface GrossPriceCalculator {

    Money getAggregatedGrossPrice(Cart cart);
}
