package com.epam.training.webshop.core.checkout;

import com.epam.training.webshop.core.checkout.model.OrderDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckoutObservable {

    private final List<CheckoutObserver> observerList;

    public void notifyObservers(OrderDto orderDto) {
        observerList.forEach(checkoutObserver -> checkoutObserver.handleOrder(orderDto));
    }
}
