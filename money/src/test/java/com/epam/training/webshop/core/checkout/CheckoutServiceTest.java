package com.epam.training.webshop.core.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.checkout.model.Order;
import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.ProductDto;
import java.util.Collections;
import java.util.Currency;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    @Mock
    private GrossPriceCalculator calculator;

    @Mock
    private Cart cart;

    @Mock
    private CheckoutObservable checkoutObservable;

    @InjectMocks
    private CheckoutService underTest;

    @Test
    void testCheckoutShouldReturnWithAnOrderWhenCartIsNotNull() {
        // Given
        Map<ProductDto, Integer> productMap = Collections.emptyMap();
        Money netPrice = new Money(1.0, Currency.getInstance("USD"));
        Money grossPrice = new Money(2.0, Currency.getInstance("USD"));
        when(cart.getProductMap()).thenReturn(productMap);
        when(cart.getAggregatedNetPrice()).thenReturn(netPrice);
        when(calculator.getAggregatedGrossPrice(cart)).thenReturn(grossPrice);
        Order expected = new Order(productMap, netPrice, grossPrice);

        // When
        Order actual = underTest.checkout(cart);

        // Then
        assertEquals(expected, actual);
        verify(cart).getProductMap();
        verify(cart).getAggregatedNetPrice();
        verify(calculator).getAggregatedGrossPrice(cart);
        verify(checkoutObservable).notifyObservers(expected);
    }
}
