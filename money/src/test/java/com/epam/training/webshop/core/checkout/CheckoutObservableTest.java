package com.epam.training.webshop.core.checkout;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.epam.training.webshop.core.checkout.model.Order;
import com.epam.training.webshop.core.finance.money.Money;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import org.junit.jupiter.api.Test;

class CheckoutObservableTest {

    private static final Currency USD_CURRENCY = Currency.getInstance("USD");

    @Test
    void testNotifyObserversShouldCallAllObserverWhenOrderIsPassed() {
        // Given
        Money netPrice = new Money(1.0, USD_CURRENCY);
        Money grossPrice = new Money(2.0, USD_CURRENCY);
        Order order = new Order(Collections.emptyMap(), netPrice, grossPrice);
        CheckoutObserver checkoutObserver = mock(CheckoutObserver.class);
        CheckoutObservable underTest = new CheckoutObservable(List.of(checkoutObserver));

        // When
        underTest.notifyObservers(order);

        // Then
        verify(checkoutObserver).handleOrder(order);
        verifyNoMoreInteractions(checkoutObserver);
    }
}
