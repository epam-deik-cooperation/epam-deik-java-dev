package com.epam.training.money.impl;

import java.util.Currency;

public class IdenticalConversionRate implements ConversionRate {
    @Override
    public boolean canConvert(Currency originalCurrency, Currency targetCurrency) {
        return originalCurrency.equals(targetCurrency);
    }

    @Override
    public double convert(double value) {
        return value;
    }
}
