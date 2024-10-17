package com.epam.training.money.command;

import com.epam.training.money.cart.Cart;
import com.epam.training.money.session.Session;

public class UserCartClearCommand extends AbstractCommand {

  public UserCartClearCommand() {
    super("user", "cart", "clear");
  }

  @Override
  protected String process(String[] commands) {
    Cart cart = Session.INSTANCE.getCart();
    cart.clear();
    return "Cart is cleared!";
  }
}
