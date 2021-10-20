package com.epam.training.webshop.core.finance.bank.impl;

import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.bank.model.CurrencyPair;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StaticBankTest {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency GBP_CURRENCY = Currency.getInstance("GBP");
    private static final CurrencyPair USD_TO_HUF_CURRENCY_PAIR = new CurrencyPair(USD_CURRENCY, HUF_CURRENCY);
    private static final CurrencyPair USD_TO_GBP_CURRENCY_PAIR = new CurrencyPair(USD_CURRENCY, GBP_CURRENCY);

    private final Bank underTest = new StaticBank();

    @Test
    public void testGetRateShouldReturnCorrectResultWhenExchangeRateExists() {
        // Given
        Double expected = 249.3;

        // When
        Double result = underTest.getExchangeRate(USD_TO_HUF_CURRENCY_PAIR).get();

        // Then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testGetRateShouldReturnCorrectResultWhenExchangeRateDoesNotExist() {
        // Given
        Optional<Double> expected = Optional.empty();

        // When
        Optional<Double> result = underTest.getExchangeRate(USD_TO_GBP_CURRENCY_PAIR);

        // Then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testGetRateShouldThrowNullPointerExceptionWhenCurrencyPairParameterIsNull() {
        // Given
        // When - Then
        Assertions.assertThrows(NullPointerException.class, () -> underTest.getExchangeRate(null));
    }

    @Test
    public void testGetRateShouldThrowNullPointerExceptionWhenFromCurrencyIsNull() {
        // Given
        // When - Then
        Assertions.assertThrows(NullPointerException.class, () -> underTest.getExchangeRate(new CurrencyPair(null, USD_CURRENCY)));
    }

    @Test
    public void testGetRateShouldReturnEmptyOptionalWhenToCurrencyIsNull() {
        // Given
        Optional<Double> expected = Optional.empty();

        // When
        Optional<Double> result = underTest.getExchangeRate(new CurrencyPair(USD_CURRENCY, null));

        // Then
        Assertions.assertEquals(expected, result);
    }
}