package com.epam.training.webshop.core.finance.money.comparator;

import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.money.Money;
import java.util.Comparator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MoneyComparator implements Comparator<Money> {

    private final Bank bank;

    @Override
    public int compare(Money money1, Money money2) {
        Money convertedMoney = money2.convert(money1.getCurrency(), bank);
        return Double.compare(money1.getAmount(), convertedMoney.getAmount());
    }
}
