package com.epam.training.ticketservice.account;

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
    public void testFindByUserName() {

        // Given
        Account testAccount = Account.builder()
                .userName("test")
                .password("test")
                .accountType(AccountType.ADMIN)
                .build();

        // When
        when(accountRepository.findByUserName(testAccount.getUserName())).thenReturn(testAccount);
        accountService.findByUserName(testAccount.getUserName());

        // Then
        verify(accountRepository, times(1)).findByUserName(testAccount.getUserName());
    }


}
