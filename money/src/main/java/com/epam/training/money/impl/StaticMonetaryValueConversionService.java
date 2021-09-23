package com.epam.training.money.impl;

import java.util.Currency;
import java.util.List;

import com.epam.training.money.MonetaryValueConversionService;
import com.epam.training.money.exception.UnknownConversionRateException;

public class StaticMonetaryValueConversionService implements MonetaryValueConversionService {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final double HUF_TO_USD_RATIO = 0.0034;
    private static final double USD_TO_HUF_RATIO = 249.3;

    private static final List<ConversionRate> CONVERSION_RATES = List.of(
            new IdenticalConversionRate(),
            new FixedConversionRate(HUF_CURRENCY, USD_CURRENCY, HUF_TO_USD_RATIO),
            new FixedConversionRate(USD_CURRENCY, HUF_CURRENCY, USD_TO_HUF_RATIO)
    );

    @Override
    public double convert(double value, Currency originalCurrency, Currency targetCurrency) {
        ConversionRate conversionRate = lookUpConversionRate(originalCurrency, targetCurrency);
        return conversionRate.convert(value);
    }

    private ConversionRate lookUpConversionRate(Currency originalCurrency, Currency targetCurrency) {
        return CONVERSION_RATES.stream()
                .filter(rate -> rate.canConvert(originalCurrency, targetCurrency))
                .findFirst()
                .orElseThrow(() -> new UnknownConversionRateException(targetCurrency, originalCurrency));
    }
}
