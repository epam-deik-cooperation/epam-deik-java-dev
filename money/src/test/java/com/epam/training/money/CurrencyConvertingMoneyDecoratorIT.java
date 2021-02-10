package com.epam.training.money;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Currency;

import org.junit.Test;

import com.epam.training.money.impl.CurrencyConvertingMoneyDecorator;
import com.epam.training.money.impl.FixedCurrencyConversionRateFactory;
import com.epam.training.money.impl.SimpleMoney;
import com.epam.training.money.impl.exception.UnsupportedCurrencyConversionException;

public class CurrencyConvertingMoneyDecoratorIT {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency GBP_CURRENCY = Currency.getInstance("GBP");

    @Test
    public void testAddReturnsExpectedResultWhenDifferentCurrencyIsUsed() {
        // Given
        Money decoratedMoney = new SimpleMoney(120, HUF_CURRENCY);
        CurrencyConversionRateFactory rateFactory = new FixedCurrencyConversionRateFactory();
        CurrencyConvertingMoneyDecorator underTest = new CurrencyConvertingMoneyDecorator(decoratedMoney, rateFactory);

        Money moneyToAdd = new SimpleMoney(1, USD_CURRENCY);

        // When
        Money result = underTest.add(moneyToAdd);

        // Then
        assertThat(result.getValue(), equalTo(369.3));
        assertThat(result.getCurrency(), equalTo(HUF_CURRENCY));
    }

    @Test
    public void testAddReturnsExpectedResultWhenMatchingCurrencyIsUsed() {
        // Given
        Money decoratedMoney = new SimpleMoney(120, HUF_CURRENCY);
        CurrencyConversionRateFactory rateFactory = new FixedCurrencyConversionRateFactory();
        CurrencyConvertingMoneyDecorator underTest = new CurrencyConvertingMoneyDecorator(decoratedMoney, rateFactory);

        Money moneyToAdd = new SimpleMoney(1, HUF_CURRENCY);

        // When
        Money result = underTest.add(moneyToAdd);

        // Then
        assertThat(result.getValue(), equalTo(121.0));
        assertThat(result.getCurrency(), equalTo(HUF_CURRENCY));
    }

    @Test(expected = UnsupportedCurrencyConversionException.class)
    public void testAddThrowsExceptionWhenCurrencyWithUnknownRateIsUsed() {
        // Given
        Money decoratedMoney = new SimpleMoney(120, HUF_CURRENCY);
        CurrencyConversionRateFactory rateFactory = new FixedCurrencyConversionRateFactory();
        CurrencyConvertingMoneyDecorator underTest = new CurrencyConvertingMoneyDecorator(decoratedMoney, rateFactory);

        Money moneyToAdd = new SimpleMoney(1, GBP_CURRENCY);

        // When
        Money result = underTest.add(moneyToAdd);

        // Then exception is thrown
    }

}
