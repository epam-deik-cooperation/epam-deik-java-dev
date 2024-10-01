package com.epam.training.money.bank;

import com.epam.training.money.model.CurrencyPair;

import java.util.Currency;
import java.util.Map;
import java.util.Optional;

public class StaticBank implements Bank {

    private static final double HUF_TO_USD_EXCHANGE_RATE = 0.0034;
    private static final double USD_TO_HUF_EXCHANGE_RATE = 249.3;
    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency HUF = Currency.getInstance("HUF");

    private final Map<CurrencyPair, Double> exchangeRates;

    public StaticBank() {
        this.exchangeRates = Map.of(
                new CurrencyPair(USD, HUF), USD_TO_HUF_EXCHANGE_RATE,
                new CurrencyPair(HUF, USD), HUF_TO_USD_EXCHANGE_RATE
        );
    }

    @Override
    public Optional<Double> getExchangeRate(CurrencyPair currencyPair) {
        return (currencyPair.from().equals(currencyPair.to()))
                ? Optional.of(1D)
                : Optional.ofNullable(
                exchangeRates.get(currencyPair)
        );
    }
}
