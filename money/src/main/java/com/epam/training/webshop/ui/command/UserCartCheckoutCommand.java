package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.ui.session.Session;

public class UserCartCheckoutCommand extends AbstractCommand {

    private final GrossPriceCalculator grossPriceCalculator;

    public UserCartCheckoutCommand(GrossPriceCalculator grossPriceCalculator) {
        super("user", "cart", "checkout");
        this.grossPriceCalculator = grossPriceCalculator;
    }

    @Override
    protected String process(String[] params) {
        Cart cart = Session.INSTANCE.getCart();
        if (cart.getProductList().isEmpty()) {
            return "You cannot checkout because cart is empty!";
        } else {
            return "To pay " + grossPriceCalculator.getAggregatedGrossPrice(cart);
        }
    }
}
