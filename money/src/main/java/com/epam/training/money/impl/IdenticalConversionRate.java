package com.epam.training.money.impl;

import java.util.Currency;
import java.util.Optional;

public class IdenticalConversionRate implements ConversionRate {
    @Override
    public boolean canConvert(Currency originalCurrency, Currency targetCurrency) {
//        return originalCurrency.equals(targetCurrency);
        return Optional.ofNullable(originalCurrency)
                .map(e -> e.equals(targetCurrency))
                .orElse(false);
    }

    @Override
    public double convert(double value) {
        return value;
    }
}
