package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.checkout.CheckoutService;

public class UserCartCheckoutCommand extends AbstractCommand {

    private final CheckoutService checkoutService;
    private final Cart cart;

    public UserCartCheckoutCommand(CheckoutService checkoutService, Cart cart) {
        super("user", "cart", "checkout");
        this.checkoutService = checkoutService;
        this.cart = cart;
    }

    @Override
    protected String process(String[] params) {
        if (cart.getProductList().isEmpty()) {
            return "You cannot checkout because cart is empty!";
        } else {
            return "Your order: " + checkoutService.checkout(cart);
        }
    }
}
