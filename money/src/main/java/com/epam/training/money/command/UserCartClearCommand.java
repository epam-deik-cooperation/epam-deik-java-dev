package com.epam.training.money.command;

import com.epam.training.money.cart.Cart;
import com.epam.training.money.session.Session;

public class UserCartClearCommand extends AbstractCommand {

    public UserCartClearCommand() {
        super("user", "cart", "clear");
    }

    @Override
    protected String process(String[] params) {
        Cart cart = Session.INSTANCE.getCart();
        if (cart.isEmpty()) {
            return "You cannot clear your cart because it is empty!";
        } else {
            cart.clear();
            return "The cart is cleared successfully!";
        }
    }
}