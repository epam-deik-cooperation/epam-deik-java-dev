package com.epam.training.money.command;

import com.epam.training.money.cart.Cart;
import com.epam.training.money.grossprice.GrossPriceCalculator;
import com.epam.training.money.session.Session;

public class UserCartCheckoutCommand extends AbstractCommand {

    private final GrossPriceCalculator grossPriceCalculator;

    public UserCartCheckoutCommand(GrossPriceCalculator grossPriceCalculator) {
        super("user", "cart", "checkout");
        this.grossPriceCalculator = grossPriceCalculator;
    }

    @Override
    protected String process(String[] commandParts) {
        Cart cart = Session.INSTANCE.getCart();
        if (cart.isEmpty()) {
            return "You cannot checkout because cart is empty!";
        } else {
            return "To pay " + grossPriceCalculator.getAggregatedGrossPrice(cart);
        }
    }
}
