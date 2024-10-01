package com.epam.training.money.impl;

import com.epam.training.money.bank.Bank;
import com.epam.training.money.model.CurrencyPair;

import java.util.Currency;

public record Money(double value, Currency currency) {

    public Money add(Money moneyToAdd, Bank bank) {
        if (isNotTheSameCurrency(moneyToAdd)) {
            moneyToAdd = exchangeMoney(moneyToAdd, bank);
        }
        return new Money(this.value + moneyToAdd.value, this.currency);
    }

    public Integer compareTo(Money moneyToCompare, Bank bank) {
        if (isNotTheSameCurrency(moneyToCompare)) {
            moneyToCompare = exchangeMoney(moneyToCompare, bank);
        }
        return Double.compare(this.value, moneyToCompare.value);
    }

    private boolean isNotTheSameCurrency(Money money) {
        return !this.currency.equals(money.currency);
    }

    private Money exchangeMoney(Money money, Bank bank) {
        Double exchangeRate = bank.getExchangeRate(
                        new CurrencyPair(money.currency, this.currency))
                .orElseThrow(() -> new UnsupportedOperationException("Can not exchange!"));
        return new Money(money.value * exchangeRate, this.currency);
    }
}