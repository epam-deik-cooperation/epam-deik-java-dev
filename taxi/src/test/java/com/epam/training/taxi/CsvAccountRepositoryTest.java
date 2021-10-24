package com.epam.training.taxi;

import com.epam.training.taxi.account.Account;
import com.epam.training.taxi.account.RegisteredAccount;
import com.epam.training.taxi.account.UnregisteredAccount;
import com.epam.training.taxi.repository.CsvAccountRepository;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CsvAccountRepositoryTest {

    private final CsvAccountRepository underTest =
            new CsvAccountRepository("src/test/resources/test_accounts.csv");

    @Test
    public void testGetAccountShouldReturnUnregisteredAccountWhenGivenUnknownAccountId() throws IOException {
        // Given
        Long accountId = 999999L;

        // When
        Account account = underTest.getAccount(accountId);

        // Then
        assertTrue(account instanceof UnregisteredAccount);
    }

    @Test
    public void testGetAccountShouldReturnRegisteredAccountWhenGivenValidAccountId() throws IOException {
        // Given
        Long accountId = 1L;
        RegisteredAccount expected = new RegisteredAccount("1","Kiss Béla",0.1);

        // When
        Account actual = underTest.getAccount(accountId);

        // Then
        assertEquals(expected, actual);
    }
}
