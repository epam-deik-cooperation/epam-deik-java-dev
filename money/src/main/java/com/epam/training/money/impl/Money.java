package com.epam.training.money.impl;

import java.util.Currency;
import java.util.Optional;

public class Money {

    public static final String HUF = "HUF";
    public static final String USD = "USD";
    public static final double HUF_TO_USD_RATIO = 0.0034;
    public static final double USD_TO_HUF_RATIO = 249.3;

    private double value;
    private Currency currency;

    public Money(double dValue, Currency currency) {
        this.value = dValue;
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money add(Money moneyToGive) {
        moneyToGive = convertMoney(moneyToGive).orElse(null);
        if (moneyToGive == null)
            return null;
        this.value += moneyToGive.getValue();
        return this;
    }

    private Optional<Money> convertMoney(Money money) {
        if (!this.currency.equals(money.getCurrency())) {
            if (this.getCurrency().equals(Currency.getInstance(USD))
                    && money.getCurrency().equals(Currency.getInstance(HUF))) {
                return Optional.of(new Money(money.value * HUF_TO_USD_RATIO, Currency.getInstance(USD)));
            }
            else if (this.getCurrency().equals(Currency.getInstance(HUF))
                    && money.getCurrency().equals(Currency.getInstance(USD))) {
                return  Optional.of(new Money(money.value * USD_TO_HUF_RATIO, Currency.getInstance(HUF)));
            }
            else
                return Optional.empty();
        }
        return Optional.of(money);
    }

    public Integer compareTo(Money m) {
        m = convertMoney(m).orElse(null);
        if (m == null) {
            return null;
        }
        return Double.compare(this.getValue(), m.getValue());
    }
}
