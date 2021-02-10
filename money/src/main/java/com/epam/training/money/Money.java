package com.epam.training.money;

import java.util.Currency;

public interface Money extends Comparable<Money> {
    double getValue();

    Currency getCurrency();

    Money add(Money moneyToAdd);
}
