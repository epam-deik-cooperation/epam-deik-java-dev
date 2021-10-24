package com.epam.training.taxi;

import com.epam.training.taxi.account.Account;
import com.epam.training.taxi.invoice.Invoice;



public class Calculator {

    private int pricePerKilometer;

    public Calculator(int pricePerKilometer) {
        this.pricePerKilometer = pricePerKilometer;
    }

    public Invoice calculate(Account account, Double distance){
        double discountPercentage = account.getDiscountPercentage();
        Double price = distance * pricePerKilometer;
        price = price - price*discountPercentage;
        return new Invoice(account.getAccountId(), distance, price, distance*pricePerKilometer*discountPercentage);
    }
}
