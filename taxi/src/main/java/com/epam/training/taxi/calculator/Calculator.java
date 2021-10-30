package com.epam.training.taxi.calculator;

import com.epam.training.taxi.account.Account;
import com.epam.training.taxi.invoice.Invoice;

public interface Calculator {

    Invoice calculate(Account account, Double distance);

}
