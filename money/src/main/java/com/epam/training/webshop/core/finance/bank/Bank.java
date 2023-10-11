package com.epam.training.webshop.core.finance.bank;

import com.epam.training.webshop.core.finance.bank.model.CurrencyPair;
import java.util.Optional;

public interface Bank {

    Optional<Double> getExchangeRate(CurrencyPair pair);
}
