package com.epam.training.webshop.core.checkout.impl;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.checkout.CheckoutService;
import com.epam.training.webshop.core.checkout.model.OrderDto;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final GrossPriceCalculator grossPriceCalculator;
    private final CheckoutObservable checkoutObservable;

    public CheckoutServiceImpl(GrossPriceCalculator grossPriceCalculator, CheckoutObservable checkoutObservable) {
        this.grossPriceCalculator = grossPriceCalculator;
        this.checkoutObservable = checkoutObservable;
    }

    @Override
    public OrderDto checkout(Cart cart) {
        Objects.requireNonNull(cart, "Cart cannot be null during checkout process");
        OrderDto orderDto = new OrderDto(cart.getProductList(), cart.getAggregatedNetPrice(), grossPriceCalculator.getAggregatedGrossPrice(cart));
        checkoutObservable.notifyObservers(orderDto);
        return orderDto;
    }
}
