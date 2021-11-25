package com.epam.training.webshop.core.checkout.impl;

import com.epam.training.webshop.core.checkout.CheckoutObserver;
import com.epam.training.webshop.core.checkout.model.OrderDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CheckoutObservable {

    private final List<CheckoutObserver> observerList;

    public CheckoutObservable(List<CheckoutObserver> observerList) {
        this.observerList = observerList;
    }

    public void notifyObservers(OrderDto orderDto) {
        observerList.forEach(observer -> observer.handleOrder(orderDto));
    }
}
