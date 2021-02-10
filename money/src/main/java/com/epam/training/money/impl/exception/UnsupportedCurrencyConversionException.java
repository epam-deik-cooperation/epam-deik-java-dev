package com.epam.training.money.impl.exception;

import java.util.Currency;

public class UnsupportedCurrencyConversionException extends RuntimeException {
    public UnsupportedCurrencyConversionException(Currency sourceCurrency, Currency targetCurrency) {
        super(String.format("Conversion rate from  %s to %s is not known.", sourceCurrency, targetCurrency));
    }
}
