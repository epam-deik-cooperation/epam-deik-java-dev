package com.epam.training.money.impl;

import java.util.Currency;
import java.util.function.Function;

import com.epam.training.money.CurrencyConversionRateFactory;
import com.epam.training.money.Money;

public class CurrencyConvertingMoneyDecorator implements Money {

    private final Money decorated;
    private final CurrencyConversionRateFactory conversionRateFactory;

    public CurrencyConvertingMoneyDecorator(Money decorated, CurrencyConversionRateFactory conversionRateFactory) {
        this.decorated = decorated;
        this.conversionRateFactory = conversionRateFactory;
    }

    @Override
    public double getValue() {
        return decorated.getValue();
    }

    @Override
    public Currency getCurrency() {
        return decorated.getCurrency();
    }

    @Override
    public Money add(Money moneyToAdd) {
        Money sumOfMoney = convertAndApply(moneyToAdd, decorated::add);
        return wrapIntoCurrencyConvertingMoneyDecorator(sumOfMoney);
    }

    @Override
    public int compareTo(Money moneyToCompareWith) {
        return convertAndApply(moneyToCompareWith, decorated::compareTo);
    }

    private <T> T convertAndApply(Money moneyToConvert, Function<Money, T> functionToApply) {
        double valueInThisCurrency = getValueInThisCurrency(moneyToConvert);
        return functionToApply.apply(createMoneyWithValue(valueInThisCurrency));
    }

    private SimpleMoney createMoneyWithValue(double valueInThisCurrency) {
        return new SimpleMoney(valueInThisCurrency, this.getCurrency());
    }

    private double getValueInThisCurrency(Money moneyToAdd) {
        return conversionRateFactory
                .create(moneyToAdd.getCurrency(), this.getCurrency())
                .convert(moneyToAdd.getValue());
    }

    private CurrencyConvertingMoneyDecorator wrapIntoCurrencyConvertingMoneyDecorator(Money moneyToWrap) {
        return new CurrencyConvertingMoneyDecorator(moneyToWrap, this.conversionRateFactory);
    }
}
