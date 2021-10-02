package com.epam.training.money.impl.money.comparator;

import java.util.Comparator;

import com.epam.training.money.impl.bank.BankImpl;
import com.epam.training.money.impl.money.Money;

public class MoneyComparator implements Comparator<Money> {
    private final BankImpl bankImpl;

    public MoneyComparator(BankImpl bankImpl) {
        this.bankImpl = bankImpl;
    }

    @Override
    public int compare(Money money1, Money money2) {
        return Double.compare(money1.getValue(), money2.convert(money1.getCurrency(), this.bankImpl).getValue());
    }
}
