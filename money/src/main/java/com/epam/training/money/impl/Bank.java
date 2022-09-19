package com.epam.training.money.impl;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Bank {

    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");

    private final Map<CurrencyPair, Double> exchangeRateMap = new HashMap<>();

    public Bank() {
        exchangeRateMap.put(new CurrencyPair(USD_CURRENCY, HUF_CURRENCY), 249.3);
        exchangeRateMap.put(new CurrencyPair(HUF_CURRENCY, USD_CURRENCY), 0.0034);
    }

    public Optional<Double> getExchangeRate(CurrencyPair currencyPair) {
        return Optional.ofNullable(exchangeRateMap.get(currencyPair));
    }
}
