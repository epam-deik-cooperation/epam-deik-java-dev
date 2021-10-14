package com.epam.training.webshop.finance.money.comparator;

import java.util.Comparator;

import com.epam.training.webshop.finance.bank.impl.BankImpl;
import com.epam.training.webshop.finance.money.Money;

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
