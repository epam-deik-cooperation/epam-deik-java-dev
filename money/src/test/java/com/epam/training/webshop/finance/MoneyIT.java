package com.epam.training.webshop.finance;

import static java.lang.Integer.signum;

import com.epam.training.webshop.finance.bank.Bank;
import com.epam.training.webshop.finance.exception.UnknownCurrencyConversionException;
import com.epam.training.webshop.finance.money.Money;
import com.epam.training.webshop.finance.money.comparator.MoneyComparator;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MoneyIT {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency GBP_CURRENCY = Currency.getInstance("GBP");

    private final Bank bank = new Bank();
    private final MoneyComparator moneyComparator = new MoneyComparator(bank);

    @Test
    public void testAddShouldReturnExpectedResultWhenDifferentCurrencyIsUsed() {
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, USD_CURRENCY);

        Money result = underTest.add(moneyToAdd, bank);

        Assertions.assertEquals(369.3, result.getAmount());
        Assertions.assertEquals(HUF_CURRENCY, result.getCurrency());
    }

    @Test
    public void testAddShouldReturnExpectedResultWhenMatchingCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, HUF_CURRENCY);

        // When
        Money result = underTest.add(moneyToAdd, bank);

        // Then
        Assertions.assertEquals(121.0, result.getAmount());
        Assertions.assertEquals(HUF_CURRENCY, result.getCurrency());
    }

    @Test
    public void testAddShouldThrowUnknownCurrencyConversionExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, GBP_CURRENCY);

        // When
        // Then
        Assertions.assertThrows(UnknownCurrencyConversionException.class, () -> underTest.add(moneyToAdd, bank));
    }

    @ParameterizedTest
    @CsvSource({"249, 1, -1", "249.3, 1, 0", "250, 0, 1"})
    public void testCompareShouldReturnExpectedResultWhenDifferentCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, USD_CURRENCY);

        // When
        int result = moneyComparator.compare(underTest, moneyToCompareWith);

        // Then
        Assertions.assertEquals(expectedSignum, signum(result));
    }

    @ParameterizedTest
    @CsvSource({"0, 100, -1", "100, 100, 0", "100, 0, 1"})
    public void testCompareShouldReturnExpectedResultWhenMatchingCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, HUF_CURRENCY);

        // When
        int result = moneyComparator.compare(underTest, moneyToCompareWith);

        // Then
        Assertions.assertEquals(expectedSignum, signum(result));
    }

    @Test
    public void testCompareShouldThrowUnknownCurrencyConversionExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(1, GBP_CURRENCY);

        // When
        // Then
        Assertions.assertThrows(UnknownCurrencyConversionException.class, () -> moneyComparator.compare(underTest, moneyToCompareWith));
    }
}
