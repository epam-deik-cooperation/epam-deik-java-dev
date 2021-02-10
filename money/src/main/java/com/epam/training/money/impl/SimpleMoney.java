package com.epam.training.money.impl;

import java.util.Currency;

import com.epam.training.money.Money;
import com.epam.training.money.impl.exception.IncompatibleCurrencyException;

public class SimpleMoney implements Money {
    private final double value;
    private final Currency currency;

    public SimpleMoney(double value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money add(Money moneyToAdd) {
        validateMoneyIsCompatible(moneyToAdd);
        return new SimpleMoney(this.getValue() + moneyToAdd.getValue(), this.currency);
    }

    public int compareTo(Money moneyToCompareWith) {
        validateMoneyIsCompatible(moneyToCompareWith);
        return Double.compare(this.getValue(), moneyToCompareWith.getValue());
    }

    private void validateMoneyIsCompatible(Money moneyToValidate) {
        if (isCurrencyDiffers(moneyToValidate.getCurrency())) {
            throw new IncompatibleCurrencyException("Failed to add money due to different currency.");
        }
    }

    private boolean isCurrencyDiffers(Currency currencyToCompareWith) {
        return !this.currency.equals(currencyToCompareWith);
    }
}
