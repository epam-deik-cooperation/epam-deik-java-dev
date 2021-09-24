package com.epam.training.taxi;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Calculator {

    public static void main(String[] args) throws IOException {
        // Get params
        Long Acc_ID = Long.valueOf(args[0]);
        Double Dist = Double.valueOf(args[1]);

        // Get acc
        String[] Acc = Files.lines(Path.of("src/main/resources/accounts.csv"))
                .map(line -> line.split(","))
                .filter(record -> Long.parseLong(record[0])==Acc_ID)
                .findFirst()
                .orElse(null);

        // Calc
        if (Acc!=null) {
            double Discount_perc = Double.parseDouble(Acc[2]);
            Double price = Dist * 110;
            price = price - price*Discount_perc;
            FileWriter Out = new FileWriter("./out.csv", true);
            Out.write(Acc_ID.toString() + "," + Dist.toString() + "," + price + "," + Dist*110*Discount_perc + System.lineSeparator());
            Out.close();
        } else {
            Double price = Dist * 110;
            FileWriter Out = new FileWriter("./out.csv", true);
            Out.write("null," + Dist.toString() + "," + price + "," + 0 + System.lineSeparator());
            Out.close();
        }
    }
}
