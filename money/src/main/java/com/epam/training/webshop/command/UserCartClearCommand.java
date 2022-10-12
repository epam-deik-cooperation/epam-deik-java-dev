package com.epam.training.webshop.command;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.session.Session;

public class UserCartClearCommand extends AbstractCommand {

    public UserCartClearCommand() {
        super("user", "cart", "clear");
    }

    @Override
    protected String process(String[] params) {
        Cart cart = Session.INSTANCE.getCart();
        if (cart.getProductList().isEmpty()) {
            return "You cannot clear your cart because it is empty!";
        } else {
            cart.clearCart();
            return "The cart is cleared successfully!";
        }
    }
}
