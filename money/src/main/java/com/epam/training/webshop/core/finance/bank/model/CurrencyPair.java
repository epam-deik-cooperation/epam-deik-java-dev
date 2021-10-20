package com.epam.training.webshop.core.finance.bank.model;

import java.util.Currency;
import java.util.Objects;

public class CurrencyPair {

    private final Currency currencyFrom;
    private final Currency currencyTo;

    public CurrencyPair(Currency currencyFrom, Currency currencyTo) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
    }

    public Currency getCurrencyFrom() {
        return currencyFrom;
    }

    public Currency getCurrencyTo() {
        return currencyTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CurrencyPair that = (CurrencyPair) o;
        return Objects.equals(currencyFrom, that.currencyFrom) && Objects.equals(currencyTo, that.currencyTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyFrom, currencyTo);
    }
}
