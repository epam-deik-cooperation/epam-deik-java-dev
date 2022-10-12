package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.ui.session.Session;

public class UserCartListCommand extends AbstractCommand {

    public UserCartListCommand() {
        super("user", "cart", "list");
    }

    @Override
    protected String process(String[] params) {
        Cart cart = Session.INSTANCE.getCart();
        if (cart.getProductList().isEmpty()) {
            return "The cart is empty!";
        } else {
            return cart.getProductList().toString();
        }
    }
}
