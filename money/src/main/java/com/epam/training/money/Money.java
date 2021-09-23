package com.epam.training.money;

import java.util.Currency;

public final class Money {

    private final double value;
    private final Currency currency;

    public Money(double value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money add(Money moneyToAdd, MonetaryValueConversionService valueConversionService) {
        Money convertedMoneyToAdd = convertMoney(moneyToAdd, valueConversionService);
        return new Money(this.getValue() + convertedMoneyToAdd.getValue(), this.getCurrency());
    }

    public int compareTo(Money convertedMoneyToCompare, MonetaryValueConversionService valueConversionService) {
        convertedMoneyToCompare = convertMoney(convertedMoneyToCompare, valueConversionService);
        return Double.compare(this.getValue(), convertedMoneyToCompare.getValue());
    }

    private Money convertMoney(Money moneyToConvert, MonetaryValueConversionService valueConversionService) {
        double convertedValue = getConvertedValue(moneyToConvert, valueConversionService);
        return new Money(convertedValue, this.currency);
    }

    private double getConvertedValue(Money moneyToConvert, MonetaryValueConversionService valueConversionService) {
        return valueConversionService.convert(moneyToConvert.getValue(), moneyToConvert.getCurrency(), this.getCurrency());
    }
}
