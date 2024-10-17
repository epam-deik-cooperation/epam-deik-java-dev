package com.epam.training.money.command;

import com.epam.training.money.cart.Cart;
import com.epam.training.money.grosspricecalculator.GrossPriceCalculator;
import com.epam.training.money.impl.Money;
import com.epam.training.money.session.Session;

public class UserCartCheckoutCommand extends AbstractCommand{

  private GrossPriceCalculator grossPriceCalculator;

  public UserCartCheckoutCommand(GrossPriceCalculator grossPriceCalculator) {
    super("user", "cart", "checkout");
    this.grossPriceCalculator = grossPriceCalculator;
  }

  @Override
  protected String process(String[] commands) {
    Cart cart = Session.INSTANCE.getCart();
    if (cart.isEmpty()) {
      return "Cart is empty!";
    } else {
      Money amountToPay = grossPriceCalculator.getAggregatedGrossPrice(cart);
      return "Amount to pay: " + amountToPay;
    }
  }
}
