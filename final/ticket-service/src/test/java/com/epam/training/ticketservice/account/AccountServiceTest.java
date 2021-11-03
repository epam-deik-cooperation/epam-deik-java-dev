package com.epam.training.ticketservice.account;

import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Test
    public void testFindByUserName() throws NotFoundException {

        // Given
        Account testAccount = Account.builder()
                .userName("test")
                .password("test")
                .accountType(AccountType.ADMIN)
                .build();

        // When
        when(accountRepository.findByUserNameContainingIgnoreCase(testAccount.getUserName())).thenReturn(testAccount);
        accountService.findByUserName(testAccount.getUserName());

        // Then
        verify(accountRepository, times(1)).findByUserNameContainingIgnoreCase(testAccount.getUserName());
    }

    @Test
    public void testFindByUserNameShouldThrowNotFoundExceptionIfUserDoesNotExist() {

        // Given
        Account testAccount = Account.builder()
                .userName("test")
                .password("test")
                .accountType(AccountType.ADMIN)
                .build();

        // When
        when(accountRepository.findByUserNameContainingIgnoreCase(testAccount.getUserName())).thenReturn(null);

        // Then
        Assertions.assertThrows(NotFoundException.class,
                () -> accountService.findByUserName(testAccount.getUserName()));


    }

    @Test
    public void testCreateAccount() {

        // Given
        String userName = "test";
        String password = "test";
        Account account = Account.builder()
                .userName(userName)
                .password(password)
                .accountType(AccountType.USER)
                .build();

        // When
        accountService.createAccount(userName, password);

        // Then
        verify(accountRepository, times(1)).save(account);
    }


}
