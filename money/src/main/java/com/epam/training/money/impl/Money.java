package com.epam.training.money.impl;

import java.util.Currency;

public class Money {

    private double value;
    private Currency currency;

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

    public Money add(Money moneyToAdd) {
        if (isNotTheSameCurrency(moneyToAdd)) {
            moneyToAdd = convert(moneyToAdd);
        }
        this.value += moneyToAdd.getValue();
        return this;
    }

    private Money convert(Money moneyToConvert) {
        Bank bank = new Bank();
        CurrencyPair myCurrencyPair = new CurrencyPair(moneyToConvert.getCurrency(), this.currency);
        Double exchangeRate = bank.getRate(myCurrencyPair);

        Money modified = new Money(moneyToConvert.value * exchangeRate, moneyToConvert.getCurrency());

        return modified;
    }

    private boolean isNotTheSameCurrency(Money moneyToAdd) {
        return !this.currency.equals(moneyToAdd.getCurrency());
    }

    public Integer compareTo(Money moneyToCompare) {
        if (isNotTheSameCurrency(moneyToCompare)) {
            moneyToCompare = convert(moneyToCompare);
        }
        return Double.compare(this.getValue(), moneyToCompare.getValue());
    }
}