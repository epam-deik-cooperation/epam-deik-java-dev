package com.epam.training.taxi;

import com.epam.training.taxi.account.Account;
import com.epam.training.taxi.exception.NegativeDistanceException;
import com.epam.training.taxi.invoice.Invoice;
import com.epam.training.taxi.invoice.SimpleInvoice;


public class Calculator {

    private final int pricePerKilometer;

    public Calculator(int pricePerKilometer) {
        this.pricePerKilometer = pricePerKilometer;
    }

    public Invoice calculate(Account account, Double distance) {
        if (distance < 0) {
            throw new NegativeDistanceException();
        }
        double discountPercentage = account.getDiscountPercentage();
        Double price = distance * pricePerKilometer;
        price = price - price * discountPercentage;
        return new SimpleInvoice(account.getAccountId(), distance, price, distance * pricePerKilometer * discountPercentage);
    }
}
