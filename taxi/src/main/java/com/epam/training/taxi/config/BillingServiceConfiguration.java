package com.epam.training.taxi.config;

import com.epam.training.taxi.service.BillingService;
import com.epam.training.taxi.calculator.Calculator;
import com.epam.training.taxi.calculator.SimpleCalculator;
import com.epam.training.taxi.persistence.CsvInvoiceWriter;
import com.epam.training.taxi.persistence.InvoiceWriter;
import com.epam.training.taxi.persistence.AccountRepository;
import com.epam.training.taxi.persistence.CsvAccountRepository;

public final class BillingServiceConfiguration {

    private BillingServiceConfiguration(){

    }

    public static BillingService getBillingService(){
        return new BillingService(getAccountRepository(), getCalculator(), getInvoiceWriter());
    }

    public static AccountRepository getAccountRepository(){
        return new CsvAccountRepository("src/main/resources/accounts.csv");
    }

    public static Calculator getCalculator(){
        return new SimpleCalculator(110);
    }

    public static InvoiceWriter getInvoiceWriter(){
        return new CsvInvoiceWriter("./out.csv");
    }
}
