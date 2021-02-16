package com.epam.training.money.impl;

import java.util.Currency;

public class Money {

    private double amount;
    private Currency currency;

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money add(Money moneyzToGiveMeh) {
        Money money = convert(moneyzToGiveMeh);
        this.amount += money.getAmount(); // Add value of the parameter to this.val
        return this;
    }

    public Integer compareTo(Money m) {
        Money money = convert(m);
        return Double.compare(this.getAmount(), money.getAmount());
    }

    public Money convert(Money other){
        if (!this.currency.equals(other.getCurrency())) { // If the two currency does not match
            if (this.getCurrency().equals(Currency.getInstance("USD")) && other.getCurrency().equals(Currency.getInstance("HUF"))) {
                other = new Money(other.amount *0.0034, Currency.getInstance("USD"));
                return other;
            }
            else if (this.getCurrency().equals(Currency.getInstance("HUF")) && other.getCurrency().equals(Currency.getInstance("USD"))) {
                other = new Money(other.amount *249.3, Currency.getInstance("HUF"));
                return other;
            }
            else{
                throw new UnsupportedOperationException();
            }
        }
        else{
            return other;
        }
    }
}
