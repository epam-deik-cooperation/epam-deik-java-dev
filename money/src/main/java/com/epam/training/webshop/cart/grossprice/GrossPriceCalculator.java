package com.epam.training.webshop.cart.grossprice;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.finance.money.Money;

public interface GrossPriceCalculator {

    Money getAggregatedGrossPrice(Cart cart);
}
