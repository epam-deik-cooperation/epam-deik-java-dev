package com.epam.training.webshop.core.finance.money.comparator;

import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.bank.model.CurrencyPair;
import com.epam.training.webshop.core.finance.money.Money;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MoneyComparatorTest {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");

    private final Bank bank = Mockito.mock(Bank.class);
    private final MoneyComparator underTest = new MoneyComparator(bank);

    @Test
    void testCompareShouldReturnPositiveNumberWhenTheFirstMoneyToCompareIsBiggerThanSecond() {
        // Given
        Money money1 = new Money(120, HUF_CURRENCY);
        Money money2 = new Money(1, USD_CURRENCY);
        CurrencyPair currencyPair = new CurrencyPair(USD_CURRENCY, HUF_CURRENCY);
        Mockito.when(bank.getExchangeRate(currencyPair)).thenReturn(Optional.of(10.0));

        // When
        int actual = underTest.compare(money1, money2);

        // Then
        Assertions.assertEquals(1, actual);
        Mockito.verify(bank).getExchangeRate(currencyPair);
    }

    @Test
    void testCompareShouldReturnNegativeNumberWhenTheFirstMoneyToCompareIsSmallerThanSecond() {
        // Given
        Money money1 = new Money(120, HUF_CURRENCY);
        Money money2 = new Money(1200, USD_CURRENCY);
        CurrencyPair currencyPair = new CurrencyPair(USD_CURRENCY, HUF_CURRENCY);
        Mockito.when(bank.getExchangeRate(currencyPair)).thenReturn(Optional.of(10.0));

        // When
        int actual = underTest.compare(money1, money2);

        // Then
        Assertions.assertEquals(-1, actual);
        Mockito.verify(bank).getExchangeRate(currencyPair);
    }

    @Test
    void testCompareShouldReturnZeroWhenTheTwoMoneyAreEqual() {
        // Given
        Money money1 = new Money(120, HUF_CURRENCY);
        Money money2 = new Money(12, USD_CURRENCY);
        CurrencyPair currencyPair = new CurrencyPair(USD_CURRENCY, HUF_CURRENCY);
        Mockito.when(bank.getExchangeRate(currencyPair)).thenReturn(Optional.of(10.0));

        // When
        int actual = underTest.compare(money1, money2);

        // Then
        Assertions.assertEquals(0, actual);
        Mockito.verify(bank).getExchangeRate(currencyPair);
    }
}
