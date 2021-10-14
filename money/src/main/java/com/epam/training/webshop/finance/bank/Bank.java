package com.epam.training.webshop.finance.bank;

import java.util.Optional;

import com.epam.training.webshop.finance.bank.model.CurrencyPair;

public interface Bank {
    Optional<Double> getRate(CurrencyPair currencyPair);
}
