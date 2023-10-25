package com.epam.training.webshop.core.checkout;

import com.epam.training.webshop.core.checkout.model.Order;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckoutObservable {

    private final List<CheckoutObserver> observerList;

    public void notifyObservers(Order order) {
        observerList.forEach(checkoutObserver -> checkoutObserver.handleOrder(order));
    }
}
