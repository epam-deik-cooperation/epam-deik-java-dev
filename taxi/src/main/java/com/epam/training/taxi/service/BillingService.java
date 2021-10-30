package com.epam.training.taxi.service;

import com.epam.training.taxi.account.Account;
import com.epam.training.taxi.calculator.Calculator;
import com.epam.training.taxi.invoice.Invoice;
import com.epam.training.taxi.persistence.InvoiceWriter;
import com.epam.training.taxi.persistence.AccountRepository;

import java.io.IOException;

public final class BillingService {

    private final AccountRepository accountRepository;
    private final Calculator calculator;
    private final InvoiceWriter invoiceWriter;

    public BillingService(AccountRepository accountRepository, Calculator calculator, InvoiceWriter invoiceWriter) {
        this.accountRepository = accountRepository;
        this.calculator = calculator;
        this.invoiceWriter = invoiceWriter;
    }

    public Account getAccount(Long accountId) throws IOException {
        return accountRepository.getAccount(accountId);
    }

    public Invoice createInvoice(Account account, Double distanceTravelled) {
        return calculator.calculate(account, distanceTravelled);
    }
    public void writeInvoice(Invoice invoice) throws IOException {
        invoiceWriter.write(invoice);
    }
}
