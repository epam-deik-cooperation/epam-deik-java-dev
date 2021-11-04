package com.epam.training.ticketservice.account;

import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountDetailsServiceTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    AccountDetailsService accountDetailsService;

    private Account testAccount;

    @BeforeEach
    private void setUp() {
        testAccount = Account.builder()
                .userName("testUser")
                .password("testPassword")
                .build();
    }

    @Test
    public void testLoadUserByUsernameShouldReturnUserDetailHavingAdminRoleIfAdminAccountExistsWithGivenUsername()
            throws UsernameNotFoundException, NotFoundException {

        // Given
        testAccount.setAccountType(AccountType.ADMIN);


        // When
        when(accountService.findByUserName(testAccount.getUserName())).thenReturn(testAccount);
        UserDetails userDetails = accountDetailsService.loadUserByUsername(testAccount.getUserName());

        // Then
        Assertions.assertTrue(userDetails.getAuthorities()
                .stream()
                .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN")));
    }


    @Test
    public void testLoadUserByUsernameShouldReturnUserDetailHavingUserRoleIfUserAccountExistsWithGivenUsername()
            throws UsernameNotFoundException, NotFoundException {

        // Given
        testAccount.setAccountType(AccountType.USER);

        // When
        when(accountService.findByUserName(testAccount.getUserName())).thenReturn(testAccount);

        UserDetails userDetails = accountDetailsService.loadUserByUsername(testAccount.getUserName());

        List<String> authorityList = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Then
        Assertions.assertFalse(authorityList.contains("ROLE_ADMIN"));
        Assertions.assertTrue(authorityList.contains("ROLE_USER"));
    }


    @Test
    public void testLoadUserByUsernameShouldThrowUsernameNotFoundExceptionIfAccountWithGivenUsernameDoesNotExist()
            throws NotFoundException {

        // Given

        // When
        when(accountService.findByUserName(testAccount.getUserName())).thenThrow(NotFoundException.class);

        // Then
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> accountDetailsService.loadUserByUsername(testAccount.getUserName()));

    }

}
