package com.epam.training.webshop.core.cart.grossprice.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.finance.money.Money;
import org.junit.jupiter.api.Test;

public class GrossPriceCalculatorDecoratorTest {

    private final Money money = mock(Money.class);
    private final Cart cart = mock(Cart.class);
    private final GrossPriceCalculator grossPriceCalculator = mock(GrossPriceCalculator.class);
    private final GrossPriceCalculatorDecorator underTest = new GrossPriceCalculatorDecorator(grossPriceCalculator);

    @Test
    public void testGetAggregatedGrossPriceShouldInvokeGrossPriceCalculator() {
        // Given
        when(grossPriceCalculator.getAggregatedGrossPrice(cart)).thenReturn(money);

        // When
        Money actual = underTest.getAggregatedGrossPrice(cart);

        // Then
        assertEquals(money, actual);
        verify(grossPriceCalculator).getAggregatedGrossPrice(cart);
        verifyNoMoreInteractions(grossPriceCalculator, cart, money);
    }
}
