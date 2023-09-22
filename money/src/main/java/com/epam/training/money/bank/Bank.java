package com.epam.training.money.bank;

import com.epam.training.money.model.CurrencyPair;
import java.util.Optional;

public interface Bank {

    Optional<Double> getExchangeRate(CurrencyPair pair);
}
