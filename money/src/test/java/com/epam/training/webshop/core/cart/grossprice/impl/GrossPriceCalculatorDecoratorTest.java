package com.epam.training.webshop.core.cart.grossprice.impl;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.finance.money.Money;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GrossPriceCalculatorDecoratorTest {

    private final Cart cart = Mockito.mock(Cart.class);
    private final GrossPriceCalculator grossPriceCalculator = Mockito.mock(GrossPriceCalculator.class);
    private final GrossPriceCalculatorDecorator underTest = new GrossPriceCalculatorDecorator(grossPriceCalculator);

    @Test
    void testGetAggregatedGrossPriceShouldInvokeGrossPriceCalculator() {
        // Given
        Money expected = new Money(100, Currency.getInstance("HUF"));
        Mockito.when(grossPriceCalculator.getAggregatedGrossPrice(cart)).thenReturn(expected);

        // When
        Money actual = underTest.getAggregatedGrossPrice(cart);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(grossPriceCalculator).getAggregatedGrossPrice(cart);
    }
}
