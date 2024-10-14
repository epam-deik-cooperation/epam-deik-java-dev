package com.epam.training.money.grosspricecalculator;

import com.epam.training.money.cart.Cart;
import com.epam.training.money.impl.Money;

public class HUGrossPriceCalculatorDecorator extends GrossPriceCalculatorDecorator {

  public HUGrossPriceCalculatorDecorator(GrossPriceCalculator grossPriceCalculator) {
    super(grossPriceCalculator);
  }

  @Override
  public Money getAggregatedGrossPrice(Cart cart) {
    return super.getAggregatedGrossPrice(cart).multiply(1.27);
  }
}
