package com.epam.training.webshop.cart.grossprice.impl;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.finance.money.Money;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GrossPriceCalculatorImplTest {

    private final Cart cart = Mockito.mock(Cart.class);
    private final GrossPriceCalculatorImpl underTest = new GrossPriceCalculatorImpl();

    @Test
    void testGetAggregatedGrossPriceShouldReturnTheAggregatedNetPrice() {
        // Given
        Money expected = new Money(100, Currency.getInstance("HUF"));
        Mockito.when(cart.getAggregatedNetPrice()).thenReturn(expected);

        // When
        Money actual = underTest.getAggregatedGrossPrice(cart);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(cart).getAggregatedNetPrice();
    }
}
