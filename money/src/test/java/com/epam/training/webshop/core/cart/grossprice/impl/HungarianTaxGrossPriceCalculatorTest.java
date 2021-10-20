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

public class HungarianTaxGrossPriceCalculatorTest {

    private final Cart cart = mock(Cart.class);
    private final Money expected = mock(Money.class);
    private final Money netPrice = mock(Money.class);
    private final GrossPriceCalculator grossPriceCalculator = mock(GrossPriceCalculator.class);
    private final HungarianTaxGrossPriceCalculator underTest = new HungarianTaxGrossPriceCalculator(grossPriceCalculator);

    @Test
    public void testGetAggregatedGrossPriceShouldReturnTheAggregatedGrossPriceWhenCartIsNotNull() {
        // Given
        when(grossPriceCalculator.getAggregatedGrossPrice(cart)).thenReturn(netPrice);
        when(netPrice.multiply(1.27D)).thenReturn(expected);

        // When
        Money actual = underTest.getAggregatedGrossPrice(cart);

        // Then
        assertEquals(expected, actual);
        verify(grossPriceCalculator).getAggregatedGrossPrice(cart);
        verify(netPrice).multiply(1.27D);
        verifyNoMoreInteractions(grossPriceCalculator, cart, netPrice, expected);
    }
}