package com.epam.training.money.model;

import java.util.Currency;
import java.util.Objects;

public class CurrencyPair {

    private final Currency from;
    private final Currency to;

    public CurrencyPair(Currency from, Currency to) {
        this.from = from;
        this.to = to;
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyPair that = (CurrencyPair) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "CurrencyPair{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
