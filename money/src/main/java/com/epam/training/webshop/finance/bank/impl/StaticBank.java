package com.epam.training.webshop.finance.bank.impl;

import com.epam.training.webshop.finance.bank.Bank;
import com.epam.training.webshop.finance.bank.model.CurrencyPair;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StaticBank implements Bank {

    private static final Currency HUF_INSTANCE = Currency.getInstance("HUF");
    private static final Currency USD_INSTANCE = Currency.getInstance("USD");
    private static final double HUF_TO_USD_EXCHANGE_RATE = 0.0034;
    private static final double USD_TO_HUF_EXCHANGE_RATE = 249.3;

    private final Map<CurrencyPair, Double> exchangeRate;

    public StaticBank() {
        exchangeRate = new HashMap<>();
        exchangeRate.put(new CurrencyPair(HUF_INSTANCE, USD_INSTANCE), HUF_TO_USD_EXCHANGE_RATE);
        exchangeRate.put(new CurrencyPair(USD_INSTANCE, HUF_INSTANCE), USD_TO_HUF_EXCHANGE_RATE);
    }

    @Override
    public Optional<Double> getExchangeRate(CurrencyPair currencyPair) {
        return currencyPair.getCurrencyFrom().equals(currencyPair.getCurrencyTo())
            ? Optional.of(1D)
            : Optional.ofNullable(this.exchangeRate.get(currencyPair));
    }
}
