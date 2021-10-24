package com.epam.training.taxi.repository;

import com.epam.training.taxi.account.Account;
import com.epam.training.taxi.account.AccountFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvAccountRepository implements AccountRepository{

    private final String pathToCsvFile;

    public CsvAccountRepository(String pathToCsvFile) {
        this.pathToCsvFile = pathToCsvFile;
    }

    @Override
    public Account getAccount(Long accountId) throws IOException {
        String[] accountArray = Files.lines(Path.of(pathToCsvFile))
                .map(line -> line.split(","))
                .filter(record -> Long.parseLong(record[0])==accountId)
                .findFirst()
                .orElse(null);

        return AccountFactory.getAccount(accountArray);
    }
}
