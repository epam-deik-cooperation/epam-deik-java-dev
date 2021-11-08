package com.epam.training.taxi;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Calculator {

    public static void calculate(String accountId, double distance) {
        try {
            CSVFileReader csvFileReader = new CSVFileReader("src/main/resources/accounts.csv", ",");
            CSVFileWriter csvFileWriter;
            csvFileWriter = new CSVFileWriter("./out.csv", ",");
            Optional<String[]> accountData = csvFileReader.getDataByAccountId(accountId);

            if (accountData.isPresent()) {
                double discountRate = Double.parseDouble(accountData.get()[2]);
                double price = distance * 110;
                price = price - price * discountRate;
                csvFileWriter.appendLine(
                        accountId,
                        String.valueOf(distance),
                        String.valueOf(price),
                        String.valueOf(distance * 110 * discountRate));
            } else {
                double price = distance * 110;
                FileWriter Out = new FileWriter("./out.csv", true);
                Out.write("null," + distance + "," + price + "," + 0 + System.lineSeparator());
                Out.close();
            }
            csvFileWriter.close();
        }  catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
