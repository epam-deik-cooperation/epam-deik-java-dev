package com.epam.training.money.impl;

import java.util.Currency;
import java.util.List;

import com.epam.training.money.CurrencyConversionRate;
import com.epam.training.money.CurrencyConversionRateFactory;
import com.epam.training.money.impl.exception.UnsupportedCurrencyConversionException;

public class FixedCurrencyConversionRateFactory implements CurrencyConversionRateFactory {

    private final static ConstantCurrencyConversionRate RATE_FOR_SAME_CURRENCY = new ConstantCurrencyConversionRate(1);

    private final List<ConversionRateListEntry> conversionRates = List.of(
            new ConversionRateListEntry("USD", "HUF", 249.3),
            new ConversionRateListEntry("HUF", "USD", 0.0034)
    );

    @Override
    public CurrencyConversionRate create(Currency sourceCurrency, Currency targetCurrency) {
        if (sourceCurrency.equals(targetCurrency)) {
            return RATE_FOR_SAME_CURRENCY;
        }
        return conversionRates.stream()
                .filter(entry -> entry.getSource() == sourceCurrency && entry.getTarget() == targetCurrency)
                .map(ConversionRateListEntry::getRate)
                .findFirst()
                .orElseThrow(() -> new UnsupportedCurrencyConversionException(sourceCurrency, targetCurrency));
    }

    private static class ConversionRateListEntry {
        private final Currency source;
        private final Currency target;
        private final ConstantCurrencyConversionRate rate;

        public ConversionRateListEntry(String source, String target, double rate) {
            this.source = Currency.getInstance(source);
            this.target = Currency.getInstance(target);
            this.rate = new ConstantCurrencyConversionRate(rate);
        }

        public Currency getSource() {
            return source;
        }

        public Currency getTarget() {
            return target;
        }

        public ConstantCurrencyConversionRate getRate() {
            return rate;
        }
    }
}
