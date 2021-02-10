package com.epam.training.money.impl;

import com.epam.training.money.CurrencyConversionRate;

public class ConstantCurrencyConversionRate implements CurrencyConversionRate {

    private final double rate;

    public ConstantCurrencyConversionRate(double rate) {
        this.rate = rate;
    }

    @Override
    public double convert(double value) {
        return value * rate;
    }
}
