package com.epam.training.money.impl;

import com.epam.training.money.bank.Bank;
import com.epam.training.money.bank.StaticBank;
import com.epam.training.money.model.CurrencyPair;

import java.util.Currency;

public class Money {

    private double value;
    private final Currency currency;
    private final Bank staticBank = new StaticBank();

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
            moneyToAdd = exchangeMoney(moneyToAdd);
        }
        this.value += moneyToAdd.value;
        return this;
    }

    public Integer compareTo(Money moneyToCompare) {
        if (isNotTheSameCurrency(moneyToCompare)) {
            moneyToCompare = exchangeMoney(moneyToCompare);
        }
        return Double.compare(this.value, moneyToCompare.value);
    }

    private boolean isNotTheSameCurrency(Money money) {
        return !this.currency.equals(money.getCurrency());
    }

    private Money exchangeMoney(Money money) {
        Double exchangeRate = staticBank.getExchangeRate(
                        new CurrencyPair(money.currency, this.currency))
                .orElseThrow(() -> new UnsupportedOperationException("Can not exchange!"));
        return new Money(money.value * exchangeRate, this.currency);
    }
}