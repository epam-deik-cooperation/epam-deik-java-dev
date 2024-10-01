package com.epam.training.money.it;

import com.epam.training.money.bank.Bank;
import com.epam.training.money.bank.StaticBank;
import com.epam.training.money.impl.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Currency;

import static java.lang.Integer.signum;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MoneyITTest {

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
        assertThat(result.value(), equalTo(369.3));
        assertThat(result.currency(), equalTo(HUF_CURRENCY));
    }

    @Test
    public void testAddReturnsExpectedResultWhenMatchingCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, HUF_CURRENCY);

        // When
        Money result = underTest.add(moneyToAdd, bank);

        // Then
        assertThat(result.value(), equalTo(121.0));
        assertThat(result.currency(), equalTo(HUF_CURRENCY));
    }

    @Test
    public void testAddShouldThrowExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, GBP_CURRENCY);

        // When - Then
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> underTest.add(moneyToAdd, bank));
    }


    @ParameterizedTest
    @CsvSource({"249, 1, -1", "249.3, 1, 0", "250, 0, 1"})
    public void testCompareToReturnsExpectedResultWhenDifferentCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, USD_CURRENCY);

        // When
        Integer result = underTest.compareTo(moneyToCompareWith, bank);

        // Then
        assertThat(signum(result), equalTo(expectedSignum));
    }

    @ParameterizedTest
    @CsvSource({"0, 100, -1", "100, 100, 0", "100, 0, 1"})
    public void testCompareToReturnsExpectedResultWhenMatchingCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, HUF_CURRENCY);

        // When
        Integer result = underTest.compareTo(moneyToCompareWith, bank);

        // Then
        assertThat(signum(result), equalTo(expectedSignum));
    }

    @Test
    public void testCompareToThrowExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(1, GBP_CURRENCY);

        // When - Then
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> underTest.compareTo(moneyToCompareWith, bank));
    }

}