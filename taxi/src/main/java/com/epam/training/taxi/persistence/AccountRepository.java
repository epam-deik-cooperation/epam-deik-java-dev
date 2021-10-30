package com.epam.training.taxi.persistence;

import com.epam.training.taxi.account.Account;

import java.io.IOException;

public interface AccountRepository {

    Account getAccount(Long accountId) throws IOException;

}
