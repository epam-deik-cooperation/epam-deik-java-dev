package com.epam.training.money.grosspricecalculator;

import com.epam.training.money.cart.Cart;
import com.epam.training.money.impl.Money;

public interface GrossPriceCalculator {

  Money getAggregatedGrossPrice(Cart cart);

}
