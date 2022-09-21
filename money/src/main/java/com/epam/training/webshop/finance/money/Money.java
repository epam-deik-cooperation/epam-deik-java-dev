package com.epam.training.webshop.finance.money;

import com.epam.training.webshop.finance.bank.Bank;
import com.epam.training.webshop.finance.bank.model.CurrencyPair;
import com.epam.training.webshop.finance.exception.UnknownCurrencyConversionException;
import java.util.Currency;
import lombok.Value;

@Value
public class Money {

    private final double amount;
    private final Currency currency;

    public Money add(Money moneyToAdd, Bank bank) {
        Money convertedMoney = moneyToAdd.convert(currency, bank);
        return new Money(amount + convertedMoney.getAmount(), currency);
    }

    public Money convert(Currency currencyTo, Bank bank) {
        CurrencyPair currencyPair = new CurrencyPair(currency, currencyTo);
        Double exchangeRate = bank.getExchangeRate(currencyPair).orElseThrow(() -> new UnknownCurrencyConversionException(currencyPair));
        return new Money(amount * exchangeRate, currencyTo);
    }
}
