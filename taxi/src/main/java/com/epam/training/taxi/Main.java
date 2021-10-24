package com.epam.training.taxi;

import com.epam.training.taxi.account.Account;
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

        // Get acc
        Account account = accountRepository.getAccount(accountId);

        // Calc
        if (account!=null) {
            double discountPercentage = Double.parseDouble(account[2]);
            Double price = distance * 110;
            price = price - price*discountPercentage;
            FileWriter Out = new FileWriter("./out.csv", true);
            Out.write(accountId.toString() + "," + distance.toString() + "," + price + "," + distance*110*discountPercentage + System.lineSeparator());
            Out.close();
        } else {
            Double price = distance * 110;
            FileWriter Out = new FileWriter("./out.csv", true);
            Out.write("null," + distance.toString() + "," + price + "," + 0 + System.lineSeparator());
            Out.close();
        }
    }
}
