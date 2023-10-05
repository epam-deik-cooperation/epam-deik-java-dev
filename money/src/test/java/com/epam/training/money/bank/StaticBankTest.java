package com.epam.training.money.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.training.money.model.CurrencyPair;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class StaticBankTest {

    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final CurrencyPair USD_HUF_CURRENCY_PAIR = new CurrencyPair(USD_CURRENCY, HUF_CURRENCY);

    private final Bank underTest = new StaticBank();

    @Test
    void testGetExchangeRateShouldReturnUsdHufExchangeRateWhenItExists() {
        // Given
        Optional<Double> expected = Optional.of(249.3);

        // When
        Optional<Double> actual = underTest.getExchangeRate(USD_HUF_CURRENCY_PAIR);

        // Then
        assertEquals(expected, actual);
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
        assertEquals(expected, actual);
    }
}
