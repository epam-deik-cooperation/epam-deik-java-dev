package com.epam.training.taxi.account;

public final class AccountFactory {

    public static Account getAccount(String[] accountStringArray){

        if(accountStringArray == null){
            return new UnregisteredAccount();
        } else {
            return new RegisteredAccount(accountStringArray[0], accountStringArray[1], Double.parseDouble(accountStringArray[2]));
        }
    }

}
