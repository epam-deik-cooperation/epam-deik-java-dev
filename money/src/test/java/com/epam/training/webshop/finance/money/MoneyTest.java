package com.epam.training.webshop.finance.money;

import static org.mockito.Mockito.verify;

import java.util.Currency;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import com.epam.training.webshop.finance.bank.impl.BankImpl;
import com.epam.training.webshop.finance.bank.model.CurrencyPair;
import com.epam.training.webshop.finance.exception.UnknownCurrencyConversionException;

class MoneyTest {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency GBP_CURRENCY = Currency.getInstance("GBP");
    private static final CurrencyPair HUF_TO_USD_CURRENCY_PAIR = new CurrencyPair(HUF_CURRENCY, USD_CURRENCY);
    private static final CurrencyPair HUF_TO_GBP_CURRENCY_PAIR = new CurrencyPair(HUF_CURRENCY, GBP_CURRENCY);
    private static final CurrencyPair USD_TO_HUF_CURRENCY_PAIR = new CurrencyPair(USD_CURRENCY, HUF_CURRENCY);
    private static final CurrencyPair GBP_TO_HUF_CURRENCY_PAIR = new CurrencyPair(GBP_CURRENCY, HUF_CURRENCY);
    private static final String HUF_CURRENCY_STRING = "HUF";

    @Test
    public void testConvertShouldReturnCorrectResultWhenExchangeRateExists() {

        //Given
        Money underTest = new Money(300, HUF_CURRENCY);
        Money expected = new Money(600, USD_CURRENCY);
        BankImpl mockBank = Mockito.mock(BankImpl.class);
        Mockito.when(mockBank.getRate(HUF_TO_USD_CURRENCY_PAIR)).thenReturn(Optional.of(2.0));
        //When
        Money result = underTest.convert(USD_CURRENCY, mockBank);

        //Then
        Assertions.assertEquals(expected, result);
        verify(mockBank).getRate(HUF_TO_USD_CURRENCY_PAIR);
    }

    @Test
    public void testConvertShouldThrowExceptionWhenExchangeRateDoesNotExist() {
        // Given
        Money money = new Money(1, HUF_CURRENCY);
        BankImpl mockBank = Mockito.mock(BankImpl.class);
        Mockito.when(mockBank.getRate(HUF_TO_GBP_CURRENCY_PAIR)).thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(UnknownCurrencyConversionException.class, () -> money.convert(GBP_CURRENCY, mockBank));

        // Then
        Mockito.verify(mockBank).getRate(HUF_TO_GBP_CURRENCY_PAIR);
        Mockito.verifyNoMoreInteractions(mockBank);
    }

    @Test
    public void testConvertShouldThrowNullPointerExceptionWhenCurrencyParameterIsNull() {
        // Given
        Money money = new Money(1, HUF_CURRENCY);
        BankImpl mockBank = Mockito.mock(BankImpl.class);

        // When
        Assertions.assertThrows(NullPointerException.class, () -> money.convert((Currency) null, mockBank));
    }

    @Test
    public void testConvertShouldThrowNullPointerExceptionWhenBankParameterIsNull() {
        // Given
        Money money = new Money(1, HUF_CURRENCY);

        // When
        Assertions.assertThrows(NullPointerException.class, () -> money.convert(USD_CURRENCY, null));
    }

    @Test
    public void testConvertWithStringParameterShouldReturnExpectedResultWhenDifferentCurrencyIsUsed() {
        // Given
        Money underTest = new Money(3, USD_CURRENCY);
        Money expected = new Money(6, HUF_CURRENCY);
        BankImpl mockBank = Mockito.mock(BankImpl.class);
        Mockito.when(mockBank.getRate(USD_TO_HUF_CURRENCY_PAIR)).thenReturn(Optional.of(2D));

        // When
        Money actual = underTest.convert(HUF_CURRENCY_STRING, mockBank);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(mockBank).getRate(USD_TO_HUF_CURRENCY_PAIR);
        Mockito.verifyNoMoreInteractions(mockBank);
    }

    @Test
    public void testConvertWithStringParameterShouldThrowNullPointerExceptionWhenCurrencyParameterIsNull() {
        // Given
        Money underTest = new Money(3, USD_CURRENCY);
        BankImpl mockBank = Mockito.mock(BankImpl.class);

        // When - Then
        Assertions.assertThrows(NullPointerException.class, () -> underTest.convert((String) null, mockBank));
    }

    @Test
    public void testConvertWithStringParameterShouldThrowExceptionWhenCurrencyParameterIsInvalid() {
        // Given
        Money underTest = new Money(3, USD_CURRENCY);
        BankImpl mockBank = Mockito.mock(BankImpl.class);

        // When - Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> underTest.convert("", mockBank));
    }

    @Test
    public void testAddShouldReturnExpectedResultWhenDifferentCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, USD_CURRENCY);
        Money expected = new Money(369.3, USD_CURRENCY);
        BankImpl mockBank = Mockito.mock(BankImpl.class);
        Money moneyToAdd = Mockito.mock(Money.class);
        Mockito.when(moneyToAdd.convert(USD_CURRENCY, mockBank)).thenReturn(new Money(249.3, HUF_CURRENCY));

        // When
        Money actual = underTest.add(moneyToAdd, mockBank);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(moneyToAdd).convert(USD_CURRENCY, mockBank);
        Mockito.verifyNoMoreInteractions(moneyToAdd);
        Mockito.verifyNoMoreInteractions(mockBank);
    }

    @Test
    public void testAddShouldThrowNullPointerExceptionWhenBankParameterIsNull() {
        // Given
        Money money = new Money(1, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, USD_CURRENCY);

        // When
        Assertions.assertThrows(NullPointerException.class, () -> money.add(moneyToAdd, null));
    }

    @Test
    public void testAddShouldThrowNullPointerExceptionWhenMoneyToAddParameterIsNull() {
        // Given
        Money money = new Money(1, HUF_CURRENCY);
        BankImpl mockBank = Mockito.mock(BankImpl.class);

        // When
        Assertions.assertThrows(NullPointerException.class, () -> money.add(null, mockBank));

        // Then
        Mockito.verifyNoMoreInteractions(mockBank);
    }

    @Test
    public void testSubtractShouldReturnsExpectedResultWhenDifferentCurrencyIsUsed() {
        // Given
        Money underTest = new Money(120, USD_CURRENCY);
        Money expected = new Money(-129.3, USD_CURRENCY);
        BankImpl mockBank = Mockito.mock(BankImpl.class);
        Money moneyToSubtract = Mockito.mock(Money.class);
        Mockito.when(moneyToSubtract.convert(USD_CURRENCY, mockBank)).thenReturn(new Money(249.3, HUF_CURRENCY));
        Mockito.when(mockBank.getRate(HUF_TO_USD_CURRENCY_PAIR)).thenReturn(Optional.of(249.3));

        // When
        Money actual = underTest.subtract(moneyToSubtract, mockBank);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(moneyToSubtract).convert(USD_CURRENCY, mockBank);
        Mockito.verifyNoMoreInteractions(moneyToSubtract, mockBank);
    }

    @Test
    public void testSubtractShouldThrowNullPointerExceptionWhenBankParameterIsNull() {
        // Given
        Money money = new Money(1, HUF_CURRENCY);
        Money moneyToAdd = new Money(1, USD_CURRENCY);

        // When
        Assertions.assertThrows(NullPointerException.class, () -> money.subtract(moneyToAdd, null));
    }

    @Test
    public void testSubtractShouldThrowNullPointerExceptionWhenMoneyToSubtractParameterIsNull() {
        // Given
        Money money = new Money(1, HUF_CURRENCY);
        BankImpl mockBank = Mockito.mock(BankImpl.class);

        // When
        Assertions.assertThrows(NullPointerException.class, () -> money.subtract(null, mockBank));

        // Then
        Mockito.verifyNoMoreInteractions(mockBank);
    }

    @ParameterizedTest
    @CsvSource({"300,1","0,0","-300,-1","150,0.5"})
    public void testMultiplyShouldReturnsExpectedResultWhenTheMultiplierIsAValidNumber(double expectedValue, double multiplier) {
        // Given
        Money underTest = new Money(300, USD_CURRENCY);
        Money expected = new Money(expectedValue, USD_CURRENCY);
        // When
        Money actual = underTest.multiply(multiplier);
        // Then
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"60, 2", "-60, -2", "240, 0.5"})
    public void testDivideShouldReturnsExpectedResultWhenTheDividerIsAValidNumber(double expectedValue, double divider) {
        // Given
        Money underTest = new Money(120, USD_CURRENCY);
        Money expected = new Money(expectedValue, USD_CURRENCY);

        // When
        Money actual = underTest.divide(divider);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDivideShouldReturnsExpectedResultWhenTheDividerIsZero() {
        // Given
        Money underTest = new Money(120, USD_CURRENCY);
        Money expected = new Money(Double.POSITIVE_INFINITY, USD_CURRENCY);

        // When
        Money actual = underTest.divide(0);

        // Then
        Assertions.assertEquals(expected, actual);
    }

}