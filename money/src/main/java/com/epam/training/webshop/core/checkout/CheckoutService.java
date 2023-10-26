package com.epam.training.webshop.core.checkout;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.checkout.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CheckoutService {

    private final GrossPriceCalculator calculator;
    private final CheckoutObservable checkoutObservable;

    public Order checkout(Cart cart) {
        Order order = new Order(cart.getProductMap(), cart.getAggregatedNetPrice(), calculator.getAggregatedGrossPrice(cart));
        checkoutObservable.notifyObservers(order);
        return order;
    }
}
