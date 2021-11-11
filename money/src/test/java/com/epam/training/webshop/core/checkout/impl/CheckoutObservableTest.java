package com.epam.training.webshop.core.checkout.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.epam.training.webshop.core.checkout.CheckoutObserver;
import com.epam.training.webshop.core.checkout.model.Order;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CheckoutObservableTest {

    @Test
    public void testNotifyObserversShouldCallAllObserverWhenOrderIsPassed() {
        // Given
        Order order = mock(Order.class);
        CheckoutObserver checkoutObserver = mock(CheckoutObserver.class);
        CheckoutObservable underTest = new CheckoutObservable(List.of(checkoutObserver));

        // When
        underTest.notifyObservers(order);

        // Then
        verify(checkoutObserver).handleOrder(order);
        verifyNoMoreInteractions(checkoutObserver);
    }
}