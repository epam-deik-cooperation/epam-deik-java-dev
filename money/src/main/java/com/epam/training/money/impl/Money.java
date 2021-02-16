package com.epam.training.money.impl;

import java.util.Currency;

public class Money {

    /**
     * val
     */
    public double val;
    public Currency c;

    /**
     * Constructor for money
     * @param dValue double value
     * @param c c
     */
    public Money(double dValue, Currency c) {
        this.val = dValue;
        this.c = c;
    }

    public double how_much() {
        return val;
    }

    public Currency what() {
        return c;
    }

    public Money add(Money moneyzToGiveMeh) {
        // Convert
        if (!this.c.equals(moneyzToGiveMeh.what())) { // If the two currency does not match
            if (this.what().equals(Currency.getInstance("USD")) && moneyzToGiveMeh.what().equals(Currency.getInstance("HUF")))
                moneyzToGiveMeh = new Money(moneyzToGiveMeh.val *0.0034, Currency.getInstance("USD"));
            else if (this.what().equals(Currency.getInstance("HUF")) && moneyzToGiveMeh.what().equals(Currency.getInstance("USD")))
                moneyzToGiveMeh = new Money(moneyzToGiveMeh.val *249.3, Currency.getInstance("HUF"));
//            else if (this.what().equals(Currency.getInstance("ASD")) && moneyzToGiveMeh.what().equals(Currency.getInstance("USD")))
//                moneyzToGiveMeh = new Money(moneyzToGiveMeh.val *249.3, Currency.getInstance("ASD"));
            else return null;
        }
        this.val += moneyzToGiveMeh.how_much(); // Add value of the parameter to this.val
        return this;
    }

    public Integer compareTo(Money m) {
        if (!this.c.equals(m.what())) {
            if (this.what().equals(Currency.getInstance("USD")) && m.what().equals(Currency.getInstance("HUF")))
                m = new Money(m.val*0.0034, Currency.getInstance("USD"));
            else if (this.what().equals(Currency.getInstance("HUF")) && m.what().equals(Currency.getInstance("USD")))
                m = new Money(m.val*249.3, Currency.getInstance("HUF"));
            else return null;
        }
        return Double.compare(this.how_much(), m.how_much());
    }
}