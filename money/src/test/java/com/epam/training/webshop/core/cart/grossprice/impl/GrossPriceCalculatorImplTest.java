package com.epam.training.webshop.core.cart.grossprice.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.finance.money.Money;
import org.junit.jupiter.api.Test;

public class GrossPriceCalculatorImplTest {

    private final Cart cart = mock(Cart.class);
    private final Money money = mock(Money.class);
    private final GrossPriceCalculatorImpl underTest = new GrossPriceCalculatorImpl();

    @Test
    public void testGetAggregatedGrossPriceShouldReturnTheAggregatedNetPriceWhenCartIsNotNull() {
        // Given
        when(cart.getAggregatedNetPrice()).thenReturn(money);

        // When
        Money actual = underTest.getAggregatedGrossPrice(cart);

        // Then
        assertEquals(money, actual);
        verify(cart).getAggregatedNetPrice();
        verifyNoMoreInteractions(cart, money);
    }

    @Test
    public void testGetAggregatedGrossPriceShouldThrowNullPointerExceptionWhenCartIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.getAggregatedGrossPrice(null));
    }
}
