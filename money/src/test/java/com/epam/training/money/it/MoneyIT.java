package com.epam.training.money.it;

import static java.lang.Integer.signum;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.training.money.Money;
import com.epam.training.money.bank.Bank;
import com.epam.training.money.bank.StaticBank;
import java.util.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MoneyIT {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency GBP_CURRENCY = Currency.getInstance("GBP");
    private static final Bank BANK = new StaticBank();

    @Test
    void testAddReturnsExpectedResultWhenDifferentCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, USD_CURRENCY);

        // When
        Money result = underTest.add(moneyToAdd, BANK);

        // Then
        assertEquals(369.3, result.amount());
        assertEquals(HUF_CURRENCY, result.currency());
    }

    @Test
    void testAddReturnsExpectedResultWhenMatchingCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, HUF_CURRENCY);

        // When
        Money result = underTest.add(moneyToAdd, BANK);

        // Then
        assertEquals(121.0, result.amount());
        assertEquals(HUF_CURRENCY, result.currency());
    }

    @Test
    void testAddThrowsExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, GBP_CURRENCY);

        // When - Then
        assertThrows(UnsupportedOperationException.class, () -> underTest.add(moneyToAdd, BANK));
    }

    @ParameterizedTest
    @CsvSource({"249, 1, -1", "249.3, 1, 0", "250, 0, 1"})
    void testCompareToReturnsExpectedResultWhenDifferentCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, USD_CURRENCY);

        // When
        Integer result = underTest.compareTo(moneyToCompareWith, BANK);

        // Then
        assertEquals(expectedSignum, signum(result));
    }

    @ParameterizedTest
    @CsvSource({"0, 100, -1", "100, 100, 0", "100, 0, 1"})
    void testCompareToReturnsExpectedResultWhenMatchingCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, HUF_CURRENCY);

        // When
        Integer result = underTest.compareTo(moneyToCompareWith, BANK);

        // Then
        assertEquals(expectedSignum, signum(result));
    }

    @Test
    void testCompareToThrowsExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(1, GBP_CURRENCY);

        // When - Then
        assertThrows(UnsupportedOperationException.class, () -> underTest.compareTo(moneyToCompareWith, BANK));
    }
}
