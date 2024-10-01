package com.epam.training.money.bank;

import com.epam.training.money.model.CurrencyPair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StaticBankTest {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency GBP_CURRENCY = Currency.getInstance("GBP");

    private Bank underTest;

    @BeforeEach
    void setUp() {
        underTest = new StaticBank();
    }

    @AfterEach
    void tearDown() {
        // CleanUp
    }

    @Test
    void testGetExchangeRateShouldReturnOneWhenProvidedSameCurrencies() {
        // Given

        // When
        Optional<Double> actual = underTest.getExchangeRate(new CurrencyPair(HUF_CURRENCY, HUF_CURRENCY));

        // Then
        assertTrue(actual.isPresent());
        assertEquals(1D, actual.get());
    }

    @Test
    void testGetExchangeRateShouldReturnExchangeValueWhenProvidedDifferentCurrencies() {
        // Given

        // When
        Optional<Double> actual = underTest.getExchangeRate(new CurrencyPair(HUF_CURRENCY, USD_CURRENCY));

        // Then
        assertTrue(actual.isPresent());
        assertEquals(0.0034, actual.get());
    }

    @Test
    void testGetExchangeRateShouldReturnExchangeValueWhenProvidedNotHandledCurrencies() {
        // Given

        // When
        Optional<Double> actual = underTest.getExchangeRate(new CurrencyPair(HUF_CURRENCY, GBP_CURRENCY));

        // Then
        assertFalse(actual.isPresent());
    }

}