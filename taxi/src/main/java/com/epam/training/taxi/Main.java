package com.epam.training.taxi;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        if(args.length == 0)
        {
            throw new IllegalArgumentException("No arguments given! <id> <distance>");
        }else {
            Long accountID = Long.valueOf(args[0]);
            Double distance = Double.valueOf(args[1]);
            Calculator calculator = new Calculator();
            calculator.recordPassengerTransport(accountID, distance, new AccountDTOImpl());
        }
    }
}
