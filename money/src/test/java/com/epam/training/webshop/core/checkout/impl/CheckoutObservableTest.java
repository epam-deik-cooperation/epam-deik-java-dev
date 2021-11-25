package com.epam.training.webshop.core.checkout.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.epam.training.webshop.core.checkout.CheckoutObserver;
import com.epam.training.webshop.core.checkout.model.OrderDto;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CheckoutObservableTest {

    @Test
    public void testNotifyObserversShouldCallAllObserverWhenOrderIsPassed() {
        // Given
        OrderDto orderDto = mock(OrderDto.class);
        CheckoutObserver checkoutObserver = mock(CheckoutObserver.class);
        CheckoutObservable underTest = new CheckoutObservable(List.of(checkoutObserver));

        // When
        underTest.notifyObservers(orderDto);

        // Then
        verify(checkoutObserver).handleOrder(orderDto);
        verifyNoMoreInteractions(checkoutObserver);
    }
}