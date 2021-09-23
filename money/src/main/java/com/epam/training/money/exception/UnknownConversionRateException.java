package com.epam.training.money.exception;

import java.util.Currency;

public class UnknownConversionRateException extends RuntimeException {
    public UnknownConversionRateException(Currency originalCurrency, Currency targetCurrency) {
        super("Failed to convert from " + originalCurrency + " to " + targetCurrency);
    }
}
