package com.epam.training.webshop.finance.bank;

import com.epam.training.webshop.finance.bank.model.CurrencyPair;
import java.util.Optional;

public interface Bank {

    Optional<Double> getExchangeRate(CurrencyPair currencyPair);
}
