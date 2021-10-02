package com.epam.training.money.model;

import java.util.Optional;

import com.epam.training.money.impl.bank.CurrencyPair;

public interface Bank {
    Optional<Double> getRate(CurrencyPair currencyPair);
}
