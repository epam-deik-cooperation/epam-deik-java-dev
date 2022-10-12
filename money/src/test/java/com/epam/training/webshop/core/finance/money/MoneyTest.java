package com.epam.training.webshop.core.finance.money;

import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.exception.UnknownCurrencyConversionException;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

class MoneyTest {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");

    private final Bank mockBank = Mockito.mock(Bank.class);
    private final Money underTest = new Money(200D, HUF_CURRENCY);

    @Test
    void testAddShouldReturnTheCorrectResultWhenCurrenciesAreTheSame() {
        // Given
        Money moneyToAdd = new Money(100D, HUF_CURRENCY);
        Money expected = new Money(300D, HUF_CURRENCY);
        Mockito.when(mockBank.getExchangeRate(ArgumentMatchers.any())).thenReturn(Optional.of(1D));

        // When
        Money actual = underTest.add(moneyToAdd, mockBank);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(mockBank).getExchangeRate(ArgumentMatchers.any());
    }

    @Test
    void testAddShouldThrowNullPointerExceptionWhenMoneyToAddIsNull() {
        // Given - When - Then
        Assertions.assertThrows(NullPointerException.class, () -> underTest.add(null, mockBank));
    }

    @Test
    void testAddShouldThrowNullPointerExceptionWhenBankIsNull() {
        // Given
        Money moneyToAdd = new Money(100d, HUF_CURRENCY);

        // When - Then
        Assertions.assertThrows(NullPointerException.class, () -> underTest.add(moneyToAdd, null));
    }

    @Test
    void testConvertShouldThrowUnknownCurrencyConversionExceptionWhenExchangeRateDoesNotExist() {
        // Given
        Mockito.when(mockBank.getExchangeRate(ArgumentMatchers.any())).thenReturn(Optional.empty());

        // When, Then
        Assertions.assertThrows(UnknownCurrencyConversionException.class, () -> underTest.convert(HUF_CURRENCY, mockBank));
        Mockito.verify(mockBank).getExchangeRate(ArgumentMatchers.any());
    }

    @Test
    void testConvertShouldReturnCorrectResultWhenExchangeRateExists() {
        // Given
        Mockito.when(mockBank.getExchangeRate(ArgumentMatchers.any())).thenReturn(Optional.of(10D));
        Currency currency = Currency.getInstance("USD");
        Money expected = new Money(2000, currency);

        // When
        Money actual = underTest.convert(currency, mockBank);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(mockBank).getExchangeRate(ArgumentMatchers.any());
    }

    @Test
    void testConvertShouldThrowNullPointerExceptionWhenCurrencyToIsNull() {
        // Given - When - Then
        Assertions.assertThrows(NullPointerException.class, () -> underTest.convert(null, mockBank));
    }

    @Test
    void testConvertShouldThrowNullPointerExceptionWhenBankIsNull() {
        // Given - When - Then
        Assertions.assertThrows(NullPointerException.class, () -> underTest.convert(HUF_CURRENCY, null));
    }

    @Test
    void testSubtractShouldThrowNullPointerExceptionWhenMoneyToSubtractIsNull() {
        // Given - When - Then
        Assertions.assertThrows(NullPointerException.class, () -> underTest.subtract(null, mockBank));
    }

    @Test
    void testSubtractShouldThrowNullPointerExceptionWhenBankIsNull() {
        // Given
        Money moneyToSubtract = new Money(100, HUF_CURRENCY);

        // When - Then
        Assertions.assertThrows(NullPointerException.class, () -> underTest.subtract(moneyToSubtract, null));
    }

    @Test
    void testSubtractShouldReturnExpectedResultWhenParametersAreValid() {
        // Given
        Money moneyToSubtract = new Money(30, HUF_CURRENCY);
        Money expected = new Money(170, HUF_CURRENCY);
        Mockito.when(mockBank.getExchangeRate(ArgumentMatchers.any())).thenReturn(Optional.of(1.0));

        // When
        Money actual = underTest.subtract(moneyToSubtract, mockBank);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"1,300", "0,0", "-1,-300"})
    void testMultiplyShouldReturnsExpectedResultWhenTheMultiplierIsAValidNumber(double multiplier, double expectedValue) {
        // Given
        Money underTest = new Money(300, HUF_CURRENCY);
        Money expected = new Money(expectedValue, HUF_CURRENCY);

        // When
        Money actual = underTest.multiply(multiplier);

        // Then
        Assertions.assertEquals(expected, actual);
    }
}
