package com.epam.training.taxi;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Calculator {

    public static void main(String[] args) throws IOException {
        // Get params
        Long accountId = Long.valueOf(args[0]);
        Double distance = Double.valueOf(args[1]);

        // Get acc
        String[] account = Files.lines(Path.of("src/main/resources/accounts.csv"))
                .map(line -> line.split(","))
                .filter(record -> Long.parseLong(record[0])==accountId)
                .findFirst()
                .orElse(null);

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
