package com.epam.training.taxi;

import com.epam.training.taxi.account.Account;
import com.epam.training.taxi.invoice.Invoice;
import com.epam.training.taxi.repository.AccountRepository;
import com.epam.training.taxi.repository.CsvAccountRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        // Get params
        Long accountId = Long.valueOf(args[0]);
        Double distance = Double.valueOf(args[1]);

        AccountRepository accountRepository = new CsvAccountRepository(Path.of("src/main/resources/accounts.csv"));
        Calculator calculator = new Calculator(110);

        // Get acc
        Account account = accountRepository.getAccount(accountId);

        // Calc
        Invoice invoice = calculator.calculate(account, distance);
    }
}
