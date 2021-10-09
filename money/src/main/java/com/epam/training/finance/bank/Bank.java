package com.epam.training.finance.bank;

import java.util.Optional;

import com.epam.training.finance.bank.model.CurrencyPair;

public interface Bank {
    Optional<Double> getRate(CurrencyPair currencyPair);
}
