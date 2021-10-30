package com.epam.training.taxi;

import com.epam.training.taxi.account.Account;
import com.epam.training.taxi.config.BillingServiceConfiguration;
import com.epam.training.taxi.invoice.Invoice;
import com.epam.training.taxi.service.BillingService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Long accountId = Long.valueOf(args[0]);
        Double distanceTravelled = Double.valueOf(args[1]);

        BillingService billingService = BillingServiceConfiguration.getBillingService();

        Account userAccount = billingService.getAccount(accountId);

        Invoice invoice = billingService.createInvoice(userAccount, distanceTravelled);

        billingService.writeInvoice(invoice);
    }
}
