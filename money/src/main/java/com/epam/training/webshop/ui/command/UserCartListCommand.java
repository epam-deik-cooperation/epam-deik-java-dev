package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.ui.session.Session;

public class UserCartListCommand extends AbstractCommand {

    public UserCartListCommand() {
        super("user", "cart", "list");
    }

    @Override
    protected String process(String[] commandParts) {
        Cart cart = Session.INSTANCE.getCart();
        if (cart.isEmpty()) {
            return "Cart is empty!";
        }
        return cart.getProductMap().toString();
    }
}
