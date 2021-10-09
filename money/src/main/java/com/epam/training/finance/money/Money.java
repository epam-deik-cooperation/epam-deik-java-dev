package com.epam.training.finance.money;

import java.util.Currency;
import java.util.Objects;

import com.epam.training.finance.bank.impl.BankImpl;
import com.epam.training.finance.bank.model.CurrencyPair;
import com.epam.training.finance.exception.UnknownCurrencyConversionException;

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
        Objects.requireNonNull(moneyToAdd, "MoneyToAdd is a mandatory parameter");
        moneyToAdd = moneyToAdd.convert(this.getCurrency(), bankImpl);
        double newValue = this.value + moneyToAdd.getValue();
        return new Money(newValue, this.currency);
    }

    public Money subtract(Money moneyToSubtract, BankImpl bankImpl) {
        Objects.requireNonNull(moneyToSubtract, "MoneyToSubtract is a mandatory parameter");
        Objects.requireNonNull(bankImpl, "bankImpl is a mandatory parameter");
        Money convertedMoney = moneyToSubtract.convert(this.currency, bankImpl);
        return new Money(this.value - convertedMoney.getValue(), this.currency);
    }

    public Money multiply(double multiplier) {
        return new Money(this.value * multiplier, this.currency);
    }

    public Money divide(double divider) {
        return new Money(this.value / divider, this.currency);
    }

    public Money convert(String currencyTo, BankImpl bankImpl) {
        return convert(Currency.getInstance(currencyTo), bankImpl);
    }

    public Money convert(Currency currencyTo, BankImpl bankImpl) {
        Objects.requireNonNull(currencyTo, "currencyTo is a mandatory parameter");
        Objects.requireNonNull(bankImpl, "bankImpl is a mandatory parameter");
        CurrencyPair myCurrencyPair = new CurrencyPair(this.getCurrency(), currencyTo);

        Double exchangeRate = bankImpl.getRate(myCurrencyPair).orElseThrow(() ->
            new UnknownCurrencyConversionException(myCurrencyPair.getCurrencyFrom(), myCurrencyPair.getCurrencyTo())
        );

        return new Money(this.value * exchangeRate, currencyTo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Money money = (Money) o;
        return Double.compare(money.value, value) == 0 && Objects.equals(currency, money.currency);
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
}