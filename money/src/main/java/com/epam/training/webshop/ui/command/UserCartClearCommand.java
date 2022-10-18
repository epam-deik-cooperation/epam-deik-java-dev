package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;

public class UserCartClearCommand extends AbstractCommand {

    private final Cart cart;

    public UserCartClearCommand(Cart cart) {
        super("user", "cart", "clear");
        this.cart = cart;
    }

    @Override
    protected String process(String[] params) {
        if (cart.getProductList().isEmpty()) {
            return "You cannot clear your cart because it is empty!";
        } else {
            cart.clearCart();
            return "The cart is cleared successfully!";
        }
    }
}
