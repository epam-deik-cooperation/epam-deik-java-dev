package com.epam.training.taxi;

public class MainApplication {

    public static void main(String[] args) {
        String accountId = args[0];
        double distance = Double.parseDouble(args[1]);

        Calculator.calculate(accountId, distance);
    }
}
