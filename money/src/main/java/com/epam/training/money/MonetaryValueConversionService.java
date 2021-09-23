package com.epam.training.money;

import java.util.Currency;

public interface MonetaryValueConversionService {
    double convert(double value, Currency originalCurrency, Currency targetCurrency);
}
