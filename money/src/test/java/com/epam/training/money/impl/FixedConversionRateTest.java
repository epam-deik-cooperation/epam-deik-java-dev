package com.epam.training.money.impl;

import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FixedConversionRateTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency HUF = Currency.getInstance("HUF");
    private static final double VALUE_TO_CONVERT = 300.0;

    @Test
    public void testCanConvertShouldReturnTrueWhenGivenConvertAbleCurrencies() {
        // Given
        final FixedConversionRate underTest = new FixedConversionRate(USD, HUF, VALUE_TO_CONVERT);
        // When
        final boolean actual = underTest.canConvert(USD, HUF);
        // Then
        Assertions.assertTrue(actual);
    }

    @Test
    public void testCanConvertShouldReturnFalseWhenGivenUnConvertAbleCurrencyPair() {
        // Given
        final FixedConversionRate underTest = new FixedConversionRate(USD, HUF, VALUE_TO_CONVERT);
        // When
        final boolean actual = underTest.canConvert(HUF, USD);
        // Then
        Assertions.assertFalse(actual);
    }

    @Test
    public void testConvertShouldReturnConvertedValueWhenGivenConvertAbleCurrencyPair() {
        // Given
        final FixedConversionRate underTest = new FixedConversionRate(USD, HUF, VALUE_TO_CONVERT);
        // When
        final double actual = underTest.convert(100.0);
        // Then
        Assertions.assertEquals(30000, actual);
    }
}
