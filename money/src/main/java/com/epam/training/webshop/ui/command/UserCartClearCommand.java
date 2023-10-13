package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.ui.session.Session;

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
