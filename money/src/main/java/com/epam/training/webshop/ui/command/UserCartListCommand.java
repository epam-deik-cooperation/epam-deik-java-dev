package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;

public class UserCartListCommand extends AbstractCommand {

    private final Cart cart;

    public UserCartListCommand(Cart cart) {
        super("user", "cart", "list");
        this.cart = cart;
    }

    @Override
    protected String process(String[] commandParts) {
        if (cart.isEmpty()) {
            return "Cart is empty!";
        }
        return cart.getProductMap().toString();
    }
}
