package com.epam.training.money.grosspricecalculator;

import com.epam.training.money.cart.Cart;
import com.epam.training.money.impl.Money;

public class GrossPriceCalculatorImpl implements GrossPriceCalculator {

  @Override
  public Money getAggregatedGrossPrice(Cart cart) {
    return cart.getAggregatedNetPrice();
  }
}
