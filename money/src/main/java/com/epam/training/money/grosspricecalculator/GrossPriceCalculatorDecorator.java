package com.epam.training.money.grosspricecalculator;

import com.epam.training.money.cart.Cart;
import com.epam.training.money.impl.Money;
import lombok.AllArgsConstructor;

@AllArgsConstructor
abstract class GrossPriceCalculatorDecorator implements GrossPriceCalculator {

  private GrossPriceCalculator grossPriceCalculator;

  @Override
  public Money getAggregatedGrossPrice(Cart cart) {
    return grossPriceCalculator.getAggregatedGrossPrice(cart);
  }
}
