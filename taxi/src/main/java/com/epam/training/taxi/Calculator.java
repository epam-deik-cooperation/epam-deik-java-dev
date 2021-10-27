package com.epam.training.taxi;

import java.io.IOException;
import java.util.List;

public class Calculator {

    private final Integer PRICE_PER_DISTANCE = 110;
    private final String delimiter = ",";

    public void recordPassengerTransport(Long ID, Double distance,AccountDTO data) throws IOException
    {
        List<Account> accountList = data.readFromCSV(AccountDTOImpl.PATH_TO_INPUT_CSV);

        Account currentAccount = getCurrentAccount(accountList,ID);

        Double price = calculatePrice(currentAccount,distance);

        String outputString = getOutputString(currentAccount,distance,price);

        data.writeToCSV(AccountDTOImpl.PATH_TO_OUT_CSV,outputString);
    }

    public Double calculatePrice(Account account, Double distance)
    {
        double price;
        if(account == null)
        {
            price = distance * PRICE_PER_DISTANCE;
        }else {
            price = distance * PRICE_PER_DISTANCE;
            price = price - price * account.getDiscount();
        }
        return price;

    }
    public String getOutputString(Account account, Double distance, Double price)
    {
        if(account == null)
        {
            return "null" + delimiter +
                    distance + delimiter +
                    price + delimiter +
                    0;
        }else
        {
            return account.getID() + delimiter +
                    distance + delimiter +
                    price + delimiter +
                    (distance * PRICE_PER_DISTANCE * account.getDiscount());
        }
    }

    public Account getCurrentAccount(List<Account> accounts, Long ID)
    {
        return accounts.stream()
                .filter(m -> ID.equals(m.getID()))
                .findFirst()
                .orElse(null);
    }

}
