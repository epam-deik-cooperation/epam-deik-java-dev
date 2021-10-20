package com.epam.training.webshop.ui.command.impl;

import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.ui.session.Session;

public class UserCheckoutCartCommand extends AbstractCommand {

    private final GrossPriceCalculator grossPriceCalculator;

    public UserCheckoutCartCommand(GrossPriceCalculator grossPriceCalculator) {
        super("user", "cart", "checkout");
        this.grossPriceCalculator = grossPriceCalculator;
    }

    @Override
    public String process(String[] params) {
        return "To pay: " + grossPriceCalculator.getAggregatedGrossPrice(Session.INSTANCE.getCart()).getValue();
    }
}

