package com.epam.training.webshop.core.cart.grossprice.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.finance.money.Money;
import java.util.Currency;
import org.junit.jupiter.api.Test;

class GrossPriceCalculatorImplTest {

    private final Cart cart = mock(Cart.class);
    private final GrossPriceCalculatorImpl underTest = new GrossPriceCalculatorImpl();

    @Test
    void testGetAggregatedGrossPriceShouldReturnTheAggregatedNetPrice() {
        // Given
        Money expected = new Money(100, Currency.getInstance("HUF"));
        when(cart.getAggregatedNetPrice()).thenReturn(expected);

        // When
        Money actual = underTest.getAggregatedGrossPrice(cart);

        // Then
        assertEquals(expected, actual);
        verify(cart).getAggregatedNetPrice();
    }
}
