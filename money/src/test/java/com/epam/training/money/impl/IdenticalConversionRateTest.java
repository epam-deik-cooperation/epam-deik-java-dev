package com.epam.training.money.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.Currency;
import java.util.stream.Stream;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
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
        //        final ConversionRate underTest = new IdenticalConversionRate();
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, USD_CURRENCY);
        // Then
        Assertions.assertTrue(actual);
        MatcherAssert.assertThat("Currencies should be identical!", actual, CoreMatchers.is(true));
    }

    @Test
    public void testCanConvertShouldReturnFalseWhenGivenDifferentCurrencies() {
        // Given
        //        final ConversionRate underTest = new IdenticalConversionRate();
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, HUF_CURRENCY);
        // Then
        Assertions.assertFalse(actual);
        MatcherAssert.assertThat(actual, CoreMatchers.is(false));
    }

    @Test
    public void testCanConvertShouldReturnFalseWhenGivenTargetCurrencyIsNull() {
        // Given
        //        final ConversionRate underTest = new IdenticalConversionRate();
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, null);
        // Then
        assertFalse(actual);
        MatcherAssert.assertThat(actual, CoreMatchers.is(false));
    }

    @Test
    public void testCanConvertShouldThrowNullPointerExceptionWhenGivenOriginalCurrencyIsNull() {
        // Given
        //        final ConversionRate underTest = new IdenticalConversionRate();
        // When - Then
        Assertions.assertThrows(NullPointerException.class, () -> underTest.canConvert(null, null));
    }

    @Test
    void testConvertShouldReturnGivenValue() {
        // Given
        //        final ConversionRate underTest = new IdenticalConversionRate();
        final double value = 9.2;
        // When
        final double actual = underTest.convert(value);
        // Then
        assertEquals(actual, value);
        MatcherAssert.assertThat(actual, CoreMatchers.equalTo(value));
    }

    //    @Disabled
    @ParameterizedTest
    @MethodSource("canConvertParameters")
    void testCanConvert(final Currency from, final Currency target, final boolean expectedResult) {
        // Given
        //        final ConversionRate underTest = new IdenticalConversionRate();
        // When
        final boolean actual = underTest.canConvert(from, target);
        // Then
        assertEquals(actual, expectedResult);
        MatcherAssert.assertThat(actual, CoreMatchers.equalTo(expectedResult));
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
