package com.epam.training.webshop.ui.command.impl;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.checkout.CheckoutService;

public class UserCheckoutCartCommand extends AbstractCommand {

    private final CheckoutService checkoutService;
    private final Cart cart;

    public UserCheckoutCartCommand(CheckoutService checkoutService, Cart cart) {
        super("user", "cart", "checkout");
        this.checkoutService = checkoutService;
        this.cart = cart;
    }

    @Override
    public String process(String[] params) {
        return "Your order: " + checkoutService.checkout(cart);
    }
}
