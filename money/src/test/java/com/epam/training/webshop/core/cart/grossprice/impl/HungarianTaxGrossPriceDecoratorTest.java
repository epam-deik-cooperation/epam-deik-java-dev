package com.epam.training.webshop.core.cart.grossprice.impl;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.finance.money.Money;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class HungarianTaxGrossPriceDecoratorTest {

    private final Cart cart = Mockito.mock(Cart.class);
    private final GrossPriceCalculator grossPriceCalculator = Mockito.mock(GrossPriceCalculator.class);
    private final HungarianTaxGrossPriceDecorator underTest = new HungarianTaxGrossPriceDecorator(grossPriceCalculator);

    @Test
    void testGetAggregatedGrossPriceShouldReturnTheAggregatedGrossPrice() {
        // Given
        Money netPrice = new Money(100, Currency.getInstance("HUF"));
        Money expected = new Money(127, Currency.getInstance("HUF"));
        Mockito.when(grossPriceCalculator.getAggregatedGrossPrice(cart)).thenReturn(netPrice);

        // When
        Money actual = underTest.getAggregatedGrossPrice(cart);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(grossPriceCalculator).getAggregatedGrossPrice(cart);
    }
}
