package com.epam.training.webshop.core.finance.money;

import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.bank.model.CurrencyPair;
import java.util.Currency;

public record Money(double amount, Currency currency) {

    public Money add(Money moneyToAdd, Bank bank) {
        if (isNotTheSameCurrency(moneyToAdd)) {
            moneyToAdd = convert(moneyToAdd, bank);
        }
        return new Money(this.amount + moneyToAdd.amount, this.currency);
    }

    public Money multiply(double multiplier) {
        return new Money(this.amount * multiplier, this.currency);
    }

    public Integer compareTo(Money moneyToCompare, Bank bank) {
        if (isNotTheSameCurrency(moneyToCompare)) {
            moneyToCompare = convert(moneyToCompare, bank);
        }
        return Double.compare(this.amount, moneyToCompare.amount);
    }

    private Money convert(Money moneyToConvert, Bank bank) {
        CurrencyPair pair = new CurrencyPair(moneyToConvert.currency, this.currency);
        Double exchangeRate = bank.getExchangeRate(pair)
            .orElseThrow(() -> new UnsupportedOperationException("Can't find exchange rate!"));
        return new Money(moneyToConvert.amount * exchangeRate, moneyToConvert.currency);
    }

    private boolean isNotTheSameCurrency(Money moneyToAdd) {
        return !this.currency.equals(moneyToAdd.currency);
    }
}
