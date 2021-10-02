package com.epam.training.money.impl.money;

import java.util.Currency;

import com.epam.training.money.impl.bank.BankImpl;
import com.epam.training.money.impl.bank.CurrencyPair;
import com.epam.training.money.impl.exception.UnknownCurrencyConversionException;

public class Money {

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

    public Money add(Money moneyToAdd, BankImpl bankImpl) {
        moneyToAdd = moneyToAdd.convert(this.getCurrency(), bankImpl);
        double newValue = this.value + moneyToAdd.getValue();
        return new Money(newValue, this.currency);
    }

    public Money convert(Currency currencyTo, BankImpl bankImpl) {
        CurrencyPair myCurrencyPair = new CurrencyPair(this.getCurrency(), currencyTo);

        Double exchangeRate = bankImpl.getRate(myCurrencyPair).orElseThrow(() ->
            new UnknownCurrencyConversionException(myCurrencyPair.getCurrencyFrom(), myCurrencyPair.getCurrencyTo())
        );

        return new Money(this.value * exchangeRate, currencyTo);
    }
}