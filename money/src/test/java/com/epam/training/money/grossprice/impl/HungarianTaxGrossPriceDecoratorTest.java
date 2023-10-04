package com.epam.training.money.grossprice.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.training.money.Money;
import com.epam.training.money.cart.Cart;
import com.epam.training.money.grossprice.GrossPriceCalculator;
import java.util.Currency;
import org.junit.jupiter.api.Test;

class HungarianTaxGrossPriceDecoratorTest {

    private final Cart cart = mock(Cart.class);
    private final GrossPriceCalculator grossPriceCalculator = mock(GrossPriceCalculator.class);
    private final HungarianTaxGrossPriceDecorator underTest = new HungarianTaxGrossPriceDecorator(grossPriceCalculator);

    @Test
    void testGetAggregatedGrossPriceShouldReturnTheAggregatedGrossPrice() {
        // Given
        Money netPrice = new Money(100, Currency.getInstance("HUF"));
        Money expected = new Money(127, Currency.getInstance("HUF"));
        when(grossPriceCalculator.getAggregatedGrossPrice(cart)).thenReturn(netPrice);

        // When
        Money actual = underTest.getAggregatedGrossPrice(cart);

        // Then
        assertEquals(expected, actual);
        verify(grossPriceCalculator).getAggregatedGrossPrice(cart);
    }
}
