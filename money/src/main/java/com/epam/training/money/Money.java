package com.epam.training.money;

import com.epam.training.money.bank.Bank;
import com.epam.training.money.bank.StaticBank;
import com.epam.training.money.model.CurrencyPair;
import java.util.Currency;

public class Money {

    private double amount;
    private final Currency currency;
    private final Bank bank = new StaticBank();

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
        this.amount += moneyToAdd.amount;
        return this;
    }

    public Integer compareTo(Money moneyToCompare) {
        if (isNotTheSameCurrency(moneyToCompare)) {
            moneyToCompare = convert(moneyToCompare);
        }
        return Double.compare(this.amount, moneyToCompare.amount);
    }

    private Money convert(Money moneyToConvert) {
        CurrencyPair pair = new CurrencyPair(moneyToConvert.currency, this.currency);
        Double exchangeRate = bank.getExchangeRate(pair)
                .orElseThrow(() -> new UnsupportedOperationException("Can't find exchange rate!"));
        return new Money(moneyToConvert.amount * exchangeRate, moneyToConvert.currency);
    }

    private boolean isNotTheSameCurrency(Money moneyToAdd) {
        return !this.currency.equals(moneyToAdd.currency);
    }
}
