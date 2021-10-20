package com.epam.training.webshop.it;

import static java.lang.Integer.signum;

import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.bank.impl.StaticBank;
import com.epam.training.webshop.core.finance.exception.UnknownCurrencyConversionException;
import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.finance.money.comparator.MoneyComparator;
import java.util.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MoneyIT {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency GBP_CURRENCY = Currency.getInstance("GBP");

    private final Bank bank = new StaticBank();

    @Test
    public void testAddReturnsExpectedResultWhenDifferentCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, USD_CURRENCY);

        // When
        Money result = underTest.add(moneyToAdd, bank);

        // Then
        Assertions.assertEquals(369.3, result.getValue());
        Assertions.assertEquals(HUF_CURRENCY, result.getCurrency());
    }

    @Test
    public void testAddReturnsExpectedResultWhenMatchingCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, HUF_CURRENCY);

        // When
        Money result = underTest.add(moneyToAdd, bank);

        // Then
        Assertions.assertEquals(121.0, result.getValue());
        Assertions.assertEquals(HUF_CURRENCY, result.getCurrency());
    }

    @Test
    public void testAddThrowsUnknownCurrencyConversionExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, GBP_CURRENCY);

        // When - Then
        Assertions.assertThrows(UnknownCurrencyConversionException.class, () -> underTest.add(moneyToAdd, bank));
    }


    @ParameterizedTest
    @CsvSource({"249, 1, -1", "249.3, 1, 0", "250, 0, 1"})
    public void testCompareToReturnsExpectedResultWhenDifferentCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, USD_CURRENCY);

        // When
        MoneyComparator moneyComparator = new MoneyComparator(bank);
        int result = moneyComparator.compare(underTest, moneyToCompareWith);

        // Then
        Assertions.assertEquals(expectedSignum, signum(result));
    }

    @ParameterizedTest
    @CsvSource({"0, 100, -1", "100, 100, 0", "100, 0, 1"})
    public void testCompareToReturnsExpectedResultWhenMatchingCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, HUF_CURRENCY);
        MoneyComparator moneyComparator = new MoneyComparator(bank);
        // When
        int result = moneyComparator.compare(underTest, moneyToCompareWith);

        // Then
        Assertions.assertEquals(expectedSignum, signum(result));
    }

    @Test
    public void testCompareToThrowsUnknownCurrencyConversionExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(1, GBP_CURRENCY);
        MoneyComparator moneyComparator = new MoneyComparator(bank);
        // When - Then
        Assertions.assertThrows(UnknownCurrencyConversionException.class, () -> moneyComparator.compare(underTest, moneyToCompareWith));
    }
}