package com.epam.training.webshop.core.checkout.impl;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.checkout.CheckoutService;
import com.epam.training.webshop.core.checkout.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckoutServiceImpl implements CheckoutService {

    private final GrossPriceCalculator grossPriceCalculator;

    @Autowired
    public CheckoutServiceImpl(GrossPriceCalculator grossPriceCalculator) {
        this.grossPriceCalculator = grossPriceCalculator;
    }

    @Override
    public Order checkout(Cart cart) {
        return new Order(cart.getProductList(), cart.getAggregatedNetPrice(), grossPriceCalculator.getAggregatedGrossPrice(cart));
    }
}
