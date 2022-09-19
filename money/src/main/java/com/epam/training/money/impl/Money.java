package com.epam.training.money.impl;

import java.util.Currency;

public class Money {

    private double amount;
    private final Currency currency;
    private final Bank bank = new Bank();

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money add(Money moneyToAdd) {
        if (isNotTheSameCurrency(moneyToAdd)) {
            moneyToAdd = convert(moneyToAdd);
        }
        this.amount += moneyToAdd.getAmount();
        return this;
    }

    public Integer compareTo(Money moneyToCompare) {
        if (isNotTheSameCurrency(moneyToCompare)) {
            moneyToCompare = convert(moneyToCompare);
        }
        return Double.compare(this.getAmount(), moneyToCompare.getAmount());
    }

    private Money convert(Money moneyToConvert) {
        CurrencyPair currencyPair = new CurrencyPair(moneyToConvert.getCurrency(), this.getCurrency());
        Double exchangeRate = bank.getExchangeRate(currencyPair).orElseThrow(() -> new UnsupportedOperationException("Could not find exchange rate!"));
        return new Money(moneyToConvert.getAmount() * exchangeRate, moneyToConvert.getCurrency());
    }

    private boolean isNotTheSameCurrency(Money money) {
        return !this.currency.equals(money.getCurrency());
    }
}