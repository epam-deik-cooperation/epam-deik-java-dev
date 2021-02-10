package com.epam.training.money;

import java.util.Currency;

public interface CurrencyConversionRateFactory {
    CurrencyConversionRate create(Currency sourceCurrency, Currency targetCurrency);
}
