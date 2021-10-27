package com.epam.training.taxi;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AccountDTOImpl implements AccountDTO{

    public static final String PATH_TO_INPUT_CSV = "src/main/resources/accounts.csv";
    public static final String PATH_TO_OUT_CSV = "./out.csv";

    private List<Account> accounts;

    @Override
    public List<Account> readFromCSV(String path) throws IOException {
        accounts = new ArrayList<>();
        Files.lines(Path.of(path))
                .map(line -> line.split(","))
                .forEach(m -> accounts.add(
                        new Account(Long.parseLong(m[0]),m[1],Double.parseDouble(m[2]))
                        ));
        return accounts;
    }

    @Override
    public void writeToCSV(String path,String text) throws IOException {
        FileWriter fw = new FileWriter(path,true);
        fw.write(text + System.lineSeparator());
        fw.close();
    }
}
