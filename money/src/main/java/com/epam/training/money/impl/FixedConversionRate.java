package com.epam.training.money.impl;

import java.util.Currency;

public final class FixedConversionRate implements ConversionRate {

    private final Currency originalCurrency;
    private final Currency targetCurrency;
    private final double rate;

    public FixedConversionRate(Currency originalCurrency, Currency targetCurrency, double rate) {
        this.originalCurrency = originalCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    @Override
    public boolean canConvert(Currency originalCurrency, Currency targetCurrency) {
        return this.originalCurrency.equals(originalCurrency)
                && this.targetCurrency.equals(targetCurrency);
    }

    @Override
    public double convert(double value) {
        return rate * value;
    }
}
