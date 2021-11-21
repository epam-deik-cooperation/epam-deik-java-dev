package com.epam.training.webshop.service.impl;

import com.epam.training.webshop.service.GrossPriceCalculator;
import com.epam.training.webshop.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class GrossPriceCalculatorDecoratorTest {

    @Mock
    private GrossPriceCalculator grossPriceCalculator;
    @Mock
    private ShoppingCartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAggregatedGrossPriceShouldDelegateGetAggregatedGrossPriceWhenGivenItCalled() {
        // Given
        final GrossPriceCalculator underTest = new GrossPriceCalculatorDecorator(grossPriceCalculator);

        // When
        underTest.getAggregatedGrossPrice(cartService);

        // Then
        Mockito.verify(grossPriceCalculator).getAggregatedGrossPrice(cartService);
        Mockito.verifyNoMoreInteractions(grossPriceCalculator);
    }
}