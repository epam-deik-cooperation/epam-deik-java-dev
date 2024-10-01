package com.epam.training.money.it;

import com.epam.training.money.bank.Bank;
import com.epam.training.money.impl.Money;
import com.epam.training.money.model.CurrencyPair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.util.Currency;
import java.util.Optional;

import static java.lang.Integer.signum;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MoneyTest {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency GBP_CURRENCY = Currency.getInstance("GBP");
    private static final CurrencyPair VALID_CURRENCY_PAIR = new CurrencyPair(USD_CURRENCY, HUF_CURRENCY);
    private static final CurrencyPair INVALID_CURRENCY_PAIR = new CurrencyPair(GBP_CURRENCY, HUF_CURRENCY);

    private final Bank bank = Mockito.mock(Bank.class);

    @Test
    public void testAddReturnsExpectedResultWhenDifferentCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, USD_CURRENCY);
        Mockito.when(bank.getExchangeRate(VALID_CURRENCY_PAIR))
                .thenReturn(Optional.of(25D));

        // When
        Money result = underTest.add(moneyToAdd, bank);

        // Then
        assertThat(result.value(), equalTo(145D));
        assertThat(result.currency(), equalTo(HUF_CURRENCY));
        Mockito.verify(bank).getExchangeRate(VALID_CURRENCY_PAIR);
        Mockito.verifyNoMoreInteractions(bank);
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
        Mockito.verifyNoInteractions(bank);
    }

    @Test
    public void testAddShouldThrowExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, GBP_CURRENCY);

        // When - Then
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> underTest.add(moneyToAdd, bank));
        Mockito.verify(bank).getExchangeRate(INVALID_CURRENCY_PAIR);
        Mockito.verifyNoMoreInteractions(bank);
    }

    @ParameterizedTest
    @CsvSource({"249, 1, -1", "249.3, 1, 0", "250, 0, 1"})
    public void testCompareToReturnsExpectedResultWhenDifferentCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
        // Given
        Money underTest = new Money(firstValue, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(secondValue, USD_CURRENCY);
        Mockito.when(bank.getExchangeRate(VALID_CURRENCY_PAIR)).thenReturn(Optional.of(249.3));

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
        Mockito.verifyNoInteractions(bank);
    }

    @Test
    public void testCompareToShouldThrowExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money underTest = new Money(120, HUF_CURRENCY);
        Money moneyToCompareWith = new Money(1, GBP_CURRENCY);

        // When - Then
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> underTest.compareTo(moneyToCompareWith, bank));
        Mockito.verify(bank).getExchangeRate(INVALID_CURRENCY_PAIR);
    }

}
