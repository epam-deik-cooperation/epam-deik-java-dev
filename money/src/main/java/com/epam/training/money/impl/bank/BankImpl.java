package com.epam.training.money.impl.bank;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.epam.training.money.model.Bank;

public class BankImpl implements Bank {

    private static final Currency HUF_INSTANCE = Currency.getInstance("HUF");
    private static final Currency USD_INSTANCE = Currency.getInstance("USD");
    private static final double HUF_TO_USD_EXCHANGE_RATE = 0.0034;
    private static final double USD_TO_HUF_EXCHANGE_RATE = 249.3;

    Map<CurrencyPair, Double> exchangeRate;

    public BankImpl() {
        exchangeRate = new HashMap<>();
        exchangeRate.put(new CurrencyPair(HUF_INSTANCE, USD_INSTANCE), HUF_TO_USD_EXCHANGE_RATE);
        exchangeRate.put(new CurrencyPair(USD_INSTANCE, HUF_INSTANCE), USD_TO_HUF_EXCHANGE_RATE);
    }

    public Optional<Double> getRate(CurrencyPair currencyPair) {
        return (currencyPair.getCurrencyFrom().equals(currencyPair.getCurrencyTo()))
            ? Optional.of(1D)
            : Optional.ofNullable(this.exchangeRate.get(currencyPair));
    }
}
