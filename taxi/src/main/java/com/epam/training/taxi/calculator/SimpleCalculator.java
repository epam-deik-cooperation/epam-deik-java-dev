package com.epam.training.taxi.calculator;

import com.epam.training.taxi.account.Account;
import com.epam.training.taxi.exception.NegativeDistanceException;
import com.epam.training.taxi.invoice.Invoice;
import com.epam.training.taxi.invoice.SimpleInvoice;


public final class SimpleCalculator implements Calculator{

    private final int pricePerKilometer;

    public SimpleCalculator(int pricePerKilometer) {
        this.pricePerKilometer = pricePerKilometer;
    }

    public Invoice calculate(Account account, Double distanceTravelled) {
        if (distanceTravelled < 0) {
            throw new NegativeDistanceException();
        }
        double discountPercentage = account.getDiscountPercentage();
        Double price = distanceTravelled * pricePerKilometer;
        price = price - price * discountPercentage;
        return new SimpleInvoice(account.getAccountId(), distanceTravelled, price, distanceTravelled * pricePerKilometer * discountPercentage);
    }
}
