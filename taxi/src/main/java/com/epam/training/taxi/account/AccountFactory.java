package com.epam.training.taxi.account;

public final class AccountFactory {

    public static Account getAccount(String[] accountArray) {

        if (accountArray == null) {
            return new UnregisteredAccount();
        } else {
            return new RegisteredAccount(accountArray[0], accountArray[1], Double.parseDouble(accountArray[2]));
        }
    }

}
