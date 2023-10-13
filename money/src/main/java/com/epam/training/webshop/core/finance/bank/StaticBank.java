package com.epam.training.webshop.core.finance.bank;

import com.epam.training.webshop.core.finance.bank.model.CurrencyPair;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StaticBank implements Bank {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Double HUF_TO_USD_RATE = 0.0034;
    private static final Double USD_TO_HUF_RATE = 249.3;

    private final Map<CurrencyPair, Double> exchangeRateMap;

    public StaticBank() {
        this.exchangeRateMap = new HashMap<>();
        this.exchangeRateMap.put(new CurrencyPair(HUF_CURRENCY, USD_CURRENCY), HUF_TO_USD_RATE);
        this.exchangeRateMap.put(new CurrencyPair(USD_CURRENCY, HUF_CURRENCY), USD_TO_HUF_RATE);
    }

    @Override
    public Optional<Double> getExchangeRate(CurrencyPair pair) {
        return Optional.ofNullable(exchangeRateMap.get(pair));
    }
}
