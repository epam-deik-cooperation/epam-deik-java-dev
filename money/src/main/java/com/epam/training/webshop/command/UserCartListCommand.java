package com.epam.training.webshop.command;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.session.Session;

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
