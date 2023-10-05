package com.epam.training.money;

import static java.lang.Integer.signum;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.epam.training.money.bank.Bank;
import com.epam.training.money.model.CurrencyPair;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MoneyTest {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency GBP_CURRENCY = Currency.getInstance("GBP");
    private static final CurrencyPair VALID_PAIR = new CurrencyPair(USD_CURRENCY, HUF_CURRENCY);
    private static final CurrencyPair INVALID_PAIR = new CurrencyPair(GBP_CURRENCY, HUF_CURRENCY);

    private final Bank bank = mock(Bank.class);

    @Test
    void testAddReturnsExpectedResultWhenDifferentCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, USD_CURRENCY);
        when(bank.getExchangeRate(VALID_PAIR)).thenReturn(Optional.of(10.0));

        // When
        Money result = underTest.add(moneyToAdd, bank);

        // Then
        assertEquals(130.0, result.amount());
        assertEquals(HUF_CURRENCY, result.currency());
        verify(bank).getExchangeRate(VALID_PAIR);
        verifyNoMoreInteractions(bank);
    }

    @Test
    void testAddReturnsExpectedResultWhenMatchingCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, HUF_CURRENCY);

        // When
        Money result = underTest.add(moneyToAdd, bank);

        // Then
        assertEquals(121.0, result.amount());
        assertEquals(HUF_CURRENCY, result.currency());
        verifyNoInteractions(bank);
    }

    @Test
    void testAddThrowsExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, GBP_CURRENCY);
        when(bank.getExchangeRate(INVALID_PAIR)).thenReturn(Optional.empty());

        // When - Then
        assertThrows(UnsupportedOperationException.class, () -> underTest.add(moneyToAdd, bank));
        verify(bank).getExchangeRate(INVALID_PAIR);
    }

    @ParameterizedTest
    @CsvSource({"249, 1, -1", "249.3, 1, 0", "250, 0, 1"})
    void testCompareToReturnsExpectedResultWhenDifferentCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, USD_CURRENCY);
        when(bank.getExchangeRate(VALID_PAIR)).thenReturn(Optional.of(249.3));

        // When
        Integer result = underTest.compareTo(moneyToCompareWith, bank);

        // Then
        assertEquals(expectedSignum, signum(result));
        verify(bank).getExchangeRate(VALID_PAIR);
        verifyNoMoreInteractions(bank);
    }

    @ParameterizedTest
    @CsvSource({"0, 100, -1", "100, 100, 0", "100, 0, 1"})
    void testCompareToReturnsExpectedResultWhenMatchingCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, HUF_CURRENCY);

        // When
        Integer result = underTest.compareTo(moneyToCompareWith, bank);

        // Then
        assertEquals(expectedSignum, signum(result));
        verifyNoInteractions(bank);
    }

    @Test
    void testCompareToThrowsExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(1, GBP_CURRENCY);
        when(bank.getExchangeRate(INVALID_PAIR)).thenReturn(Optional.empty());

        // When - Then
        assertThrows(UnsupportedOperationException.class, () -> underTest.compareTo(moneyToCompareWith, bank));
        verify(bank).getExchangeRate(INVALID_PAIR);
    }
}
