package com.epam.training.money.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Currency;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IdenticalConversionRateTest {

    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");

    private ConversionRate underTest;

    @BeforeEach
    void setUp() {
        underTest = new IdenticalConversionRate();
    }

    @Test
    public void testCanConvertShouldReturnTrueWhenGivenIdenticalCurrencies() {
        // Given
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, USD_CURRENCY);
        // Then
        assertTrue(actual);
    }

    @Test
    public void testCanConvertShouldReturnFalseWhenGivenDifferentCurrencies() {
        // Given
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, HUF_CURRENCY);
        // Then
        assertFalse(actual);
    }

    @Test
    public void testCanConvertShouldReturnFalseWhenGivenTargetCurrencyIsNull() {
        // Given
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, null);
        // Then
        assertFalse(actual);
    }

    @Test
    public void testCanConvertShouldThrowNullPointerExceptionWhenGivenOriginalCurrencyIsNull() {
        // Given
        // When
        final boolean actual = underTest.canConvert(null, null);
        // Then
        assertFalse(actual);
    }

    @Test
    public void testConvertShouldReturnGivenValue() {
        // Given
        final double value = 9.2;
        // When
        final double actual = underTest.convert(value);
        // Then
        assertEquals(actual, value);
    }

    /* All of the other test methods which test the {@link IdenticalConversionRate#canConvert} method
     * can be replaced by this parameterized test because it covers same test cases.
     */
    @ParameterizedTest
    @MethodSource("canConvertParameters")
    public void testCanConvert(final Currency from, final Currency target, final boolean expectedResult) {
        // Given
        // When
        final boolean actual = underTest.canConvert(from, target);
        // Then
        assertEquals(actual, expectedResult);
    }

    private static Stream<Arguments> canConvertParameters() {
        return Stream.of(
                Arguments.of(null, null, false),
                Arguments.of(USD_CURRENCY, null, false),
                Arguments.of(USD_CURRENCY, USD_CURRENCY, true),
                Arguments.of(USD_CURRENCY, HUF_CURRENCY, false)
        );
    }
}
