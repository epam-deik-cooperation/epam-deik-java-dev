package com.epam.training.money.command;

import com.epam.training.money.cart.Cart;
import com.epam.training.money.session.Session;

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
