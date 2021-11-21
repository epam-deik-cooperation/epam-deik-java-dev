package com.epam.training.webshop.service.impl;

import com.epam.training.webshop.service.GrossPriceCalculator;
import com.epam.training.webshop.service.ShoppingCartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class HungarianTaxGrossPriceCalculatorTest {

    @Mock
    private GrossPriceCalculator grossPriceCalculator;
    @Mock
    private ShoppingCartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAggregatedGrossPriceShouldReturnGrossPriceOfTheCartWhenGivenCartService() {
        // Given
        BDDMockito.given(grossPriceCalculator.getAggregatedGrossPrice(cartService)).willReturn(100.0);
        final double expectedValue = 125.00;
        final GrossPriceCalculatorDecorator underTest = new HungarianTaxGrossPriceCalculator(grossPriceCalculator, 1.25);

        // When
        final double actual = underTest.getAggregatedGrossPrice(cartService);

        // Then
        Assertions.assertEquals(expectedValue, actual);
    }
}