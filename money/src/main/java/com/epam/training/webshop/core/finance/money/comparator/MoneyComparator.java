package com.epam.training.webshop.core.finance.money.comparator;

import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.money.Money;
import java.util.Comparator;

public class MoneyComparator implements Comparator<Money> {

    private final Bank bank;

    public MoneyComparator(Bank bank) {
        this.bank = bank;
    }

    @Override
    public int compare(Money money1, Money money2) {
        return Double.compare(money1.getValue(), money2.convert(money1.getCurrency(), this.bank).getValue());
    }
}
