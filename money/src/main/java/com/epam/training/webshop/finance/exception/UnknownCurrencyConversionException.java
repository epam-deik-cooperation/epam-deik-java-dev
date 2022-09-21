package com.epam.training.webshop.finance.exception;

import com.epam.training.webshop.finance.bank.model.CurrencyPair;

public class UnknownCurrencyConversionException extends RuntimeException {

    public UnknownCurrencyConversionException(CurrencyPair currencyPair) {
        super("Failed to convert from: " + currencyPair.getCurrencyFrom() + " to: " + currencyPair.getCurrencyTo());
    }
}
