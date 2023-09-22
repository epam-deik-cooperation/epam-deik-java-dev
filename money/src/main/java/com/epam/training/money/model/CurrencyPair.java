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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyPair pair = (CurrencyPair) o;
        return Objects.equals(from, pair.from) && Objects.equals(to, pair.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
