package com.epam.training.webshop.finance;

import static java.lang.Integer.signum;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.epam.training.webshop.finance.bank.impl.BankImpl;
import com.epam.training.webshop.finance.exception.UnknownCurrencyConversionException;
import com.epam.training.webshop.finance.money.Money;
import com.epam.training.webshop.finance.money.comparator.MoneyComparator;

public class MoneyIT {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency GBP_CURRENCY = Currency.getInstance("GBP");
    private final BankImpl bankImpl = new BankImpl();

    @Test
    public void testAddReturnsExpectedResultWhenDifferentCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, USD_CURRENCY);

        // When
        Money result = underTest.add(moneyToAdd, bankImpl);

        // Then
        assertThat(result.getValue(), equalTo(369.3));
        assertThat(result.getCurrency(), equalTo(HUF_CURRENCY));
    }

    @Test
    public void testAddReturnsExpectedResultWhenMatchingCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, HUF_CURRENCY);

        // When
        Money result = underTest.add(moneyToAdd, bankImpl);

        // Then
        assertThat(result.getValue(), equalTo(121.0));
        assertThat(result.getCurrency(), equalTo(HUF_CURRENCY));
    }

    @Test
    public void testAddThrowsUnknownCurrencyConversionExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, GBP_CURRENCY);

        // When - Then
        Assertions.assertThrows(UnknownCurrencyConversionException.class, () -> underTest.add(moneyToAdd, bankImpl));
    }


    @ParameterizedTest
    @CsvSource({"249, 1, -1", "249.3, 1, 0", "250, 0, 1"})
    public void testCompareToReturnsExpectedResultWhenDifferentCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, USD_CURRENCY);

        // When
        MoneyComparator moneyComparator = new MoneyComparator(bankImpl);
        int result = moneyComparator.compare(underTest, moneyToCompareWith);

        // Then
        assertThat(signum(result), equalTo(expectedSignum));
    }

    @ParameterizedTest
    @CsvSource({"0, 100, -1", "100, 100, 0", "100, 0, 1"})
    public void testCompareToReturnsExpectedResultWhenMatchingCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, HUF_CURRENCY);
        MoneyComparator moneyComparator = new MoneyComparator(bankImpl);
        // When
        int result = moneyComparator.compare(underTest, moneyToCompareWith);

        // Then
        assertThat(signum(result), equalTo(expectedSignum));
    }

    @Test
    public void testCompareToThrowsUnknownCurrencyConversionExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(1, GBP_CURRENCY);
        MoneyComparator moneyComparator = new MoneyComparator(bankImpl);
        // When - Then
        Assertions.assertThrows(UnknownCurrencyConversionException.class, () -> moneyComparator.compare(underTest, moneyToCompareWith));
    }

}