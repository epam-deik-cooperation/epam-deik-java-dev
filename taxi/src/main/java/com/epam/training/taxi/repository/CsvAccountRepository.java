package com.epam.training.taxi.repository;

import com.epam.training.taxi.account.Account;
import com.epam.training.taxi.account.AccountFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvAccountRepository implements AccountRepository{

    private final Path pathToCsvFile;

    public CsvAccountRepository(Path pathToCsvFile) {
        this.pathToCsvFile = pathToCsvFile;
    }

    @Override
    public Account getAccount(Long accountId) throws IOException {
        return Files.lines(pathToCsvFile)
                .map(line -> line.split(","))
                .filter(record -> Long.parseLong(record[0])==accountId)
                .findFirst()
                .map(AccountFactory::getAccount)
                .orElseThrow(UnknownError::new);
    }
}
