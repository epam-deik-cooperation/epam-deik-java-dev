package com.epam.training.money;

import java.util.Currency;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Money money = (Money) o;
        return Double.compare(money.value, value) == 0 && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    @Override
    public String toString() {
        return "Money{" +
                "value=" + value +
                ", currency=" + currency +
                '}';
    }

    private Money convertMoney(Money moneyToConvert, MonetaryValueConversionService valueConversionService) {
        double convertedValue = getConvertedValue(moneyToConvert, valueConversionService);
        return new Money(convertedValue, this.currency);
    }

    private double getConvertedValue(Money moneyToConvert, MonetaryValueConversionService valueConversionService) {
        return valueConversionService.convert(moneyToConvert.getValue(), moneyToConvert.getCurrency(), this.getCurrency());
    }
}
