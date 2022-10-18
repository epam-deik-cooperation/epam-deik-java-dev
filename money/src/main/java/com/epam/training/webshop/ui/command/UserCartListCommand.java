package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;

public class UserCartListCommand extends AbstractCommand {

    private final Cart cart;

    public UserCartListCommand(Cart cart) {
        super("user", "cart", "list");
        this.cart = cart;
    }

    @Override
    protected String process(String[] params) {
        if (cart.getProductList().isEmpty()) {
            return "The cart is empty!";
        } else {
            return cart.getProductList().toString();
        }
    }
}
