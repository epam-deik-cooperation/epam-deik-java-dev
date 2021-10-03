package com.epam.training.money.impl;

import com.epam.training.money.MonetaryValueConversionService;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StaticMonetaryValueConversionServiceTest {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");

    @Test
    void testConvertShouldReturnConvertedValueWhenGivenHandledConversionRate() {
        // Given
        final MonetaryValueConversionService underTest = new StaticMonetaryValueConversionService();
        // When
        final double actual = underTest.convert(1000, HUF_CURRENCY, USD_CURRENCY);
        // Then
        Assertions.assertEquals(3.4, actual);
    }
}
