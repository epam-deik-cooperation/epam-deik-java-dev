package com.epam.training.webshop.core.finance.money;

import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.bank.model.CurrencyPair;
import com.epam.training.webshop.core.finance.exception.UnknownCurrencyConversionException;
import java.util.Currency;
import java.util.Objects;

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

    public Money add(Money moneyToAdd, Bank bank) {
        Objects.requireNonNull(moneyToAdd, "MoneyToAdd is a mandatory parameter");
        moneyToAdd = moneyToAdd.convert(this.getCurrency(), bank);
        double newValue = this.value + moneyToAdd.getValue();
        return new Money(newValue, this.currency);
    }

    public Money subtract(Money moneyToSubtract, Bank bank) {
        Objects.requireNonNull(moneyToSubtract, "MoneyToSubtract is a mandatory parameter");
        Objects.requireNonNull(bank, "bankImpl is a mandatory parameter");
        Money convertedMoney = moneyToSubtract.convert(this.currency, bank);
        return new Money(this.value - convertedMoney.getValue(), this.currency);
    }

    public Money multiply(double multiplier) {
        return new Money(this.value * multiplier, this.currency);
    }

    public Money divide(double divider) {
        return new Money(this.value / divider, this.currency);
    }

    public Money convert(String currencyTo, Bank bank) {
        return convert(Currency.getInstance(currencyTo), bank);
    }

    public Money convert(Currency currencyTo, Bank bank) {
        Objects.requireNonNull(currencyTo, "currencyTo is a mandatory parameter");
        Objects.requireNonNull(bank, "bankImpl is a mandatory parameter");
        CurrencyPair myCurrencyPair = new CurrencyPair(this.getCurrency(), currencyTo);
        Double exchangeRate = bank.getExchangeRate(myCurrencyPair).orElseThrow(() ->
            new UnknownCurrencyConversionException(myCurrencyPair.getCurrencyFrom(), myCurrencyPair.getCurrencyTo()));
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