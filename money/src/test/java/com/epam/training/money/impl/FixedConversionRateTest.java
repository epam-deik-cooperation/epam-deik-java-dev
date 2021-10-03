package com.epam.training.money.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FixedConversionRateTest {

    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency EUR_CURRENCY = Currency.getInstance("EUR");
    private static final double VALUE_TO_CONVERT = 300.0;

    private ConversionRate underTest;

    @BeforeEach
    void setUp() {
        underTest = new FixedConversionRate(USD_CURRENCY, HUF_CURRENCY, VALUE_TO_CONVERT);
    }

    @Test
    public void testCanConvertShouldReturnTrueWhenGivenConvertAbleCurrencies() {
        // Given
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, HUF_CURRENCY);
        // Then
        assertTrue(actual);
    }

    @Test
    public void testCanConvertShouldReturnFalseWhenGivenUnConvertAbleCurrencyPair() {
        // Given
        // When
        final boolean actual = underTest.canConvert(HUF_CURRENCY, USD_CURRENCY);
        // Then
        assertFalse(actual);
    }

    @Test
    public void testCanConvertShouldReturnFalseWhenGivenOriginalCurrencyIsUnknown() {
        // Given
        // When
        final boolean actual = underTest.canConvert(EUR_CURRENCY, USD_CURRENCY);
        // Then
        assertFalse(actual);
    }

    @Test
    public void testCanConvertShouldReturnFalseWhenGivenTargetCurrencyIsUnknown() {
        // Given
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, EUR_CURRENCY);
        // Then
        assertFalse(actual);
    }

    @Test
    public void testConvertShouldReturnConvertedValueWhenGivenConvertAbleCurrencyPair() {
        // Given
        // When
        final double actual = underTest.convert(100.0);
        // Then
        assertEquals(30000, actual);
    }
}
