package com.epam.training.webshop.core.finance.bank;

import com.epam.training.webshop.core.finance.bank.model.CurrencyPair;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BankTest {

    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final CurrencyPair USD_HUF_CURRENCY_PAIR = new CurrencyPair(USD_CURRENCY, HUF_CURRENCY);

    private final Bank underTest = new Bank();

    @Test
    void testGetExchangeRateShouldReturnUsdHufExchangeRateWhenItExists() {
        // Given
        Optional<Double> expected = Optional.of(249.3);

        // When
        Optional<Double> actual = underTest.getExchangeRate(USD_HUF_CURRENCY_PAIR);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetExchangeRateShouldReturnOneWhenCurrenciesAreTheSame() {
        // Given
        Optional<Double> expected = Optional.of(1D);
        CurrencyPair samePair = new CurrencyPair(USD_CURRENCY, USD_CURRENCY);

        // When
        Optional<Double> actual = underTest.getExchangeRate(samePair);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetExchangeRateShouldReturnOptionalEmptyWhenExchangeRateDoesNotExist() {
        // Given
        Optional<Double> expected = Optional.empty();
        Currency eurCurrency = Currency.getInstance("EUR");
        CurrencyPair notExistPair = new CurrencyPair(USD_CURRENCY, eurCurrency);

        // When
        Optional<Double> actual = underTest.getExchangeRate(notExistPair);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetExchangeRateShouldThrowExceptionWhenCurrencyPairIsNull() {
        Assertions.assertThrows(NullPointerException.class, () -> underTest.getExchangeRate(null));
    }
}
