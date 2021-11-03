package com.epam.training.ticketservice.account;

import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountCommandsTest {

    private Account account;

    @Mock
    AccountService accountService;

    @InjectMocks
    AccountCommands accountCommands;

    @BeforeEach
    private void setUp() {
        account = Account.builder().userName("user")
                .password("pw")
                .bookings(List.of())
                .build();
    }

    @AfterEach
    private void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void describeAccountShouldReturnAdminDescriptionIfUserWithAdminRoleIsSignedIn() throws NotFoundException {

        // Given
        Authentication authentication = new TestingAuthenticationToken(
                account.getUserName(),
                account,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String expectedDescription = String.format("Signed in with privileged account '%s'", account.getUserName());

        // When
        when(accountService.findByUserName(account.getUserName())).thenReturn(account);
        String actualDescription = accountCommands.describeAccount();

        // Then
        Assertions.assertEquals(expectedDescription, actualDescription);

        SecurityContextHolder.clearContext();
    }

    @Test
    public void describeAccountShouldReturnUserDescriptionIfUserWithUserRoleIsSignedIn() throws NotFoundException {

        // Given
        Authentication authentication = new TestingAuthenticationToken(
                account.getUserName(),
                account,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Signed in with account '%s' \n", account.getUserName()));
        account.getBookings().forEach(x -> sb.append(x.toString())
                .append("\n"));
        sb.append((account.getBookings().isEmpty() ? "You have not booked any tickets yet" : ""));

        String expectedDescription = sb.toString();

        // When
        when(accountService.findByUserName(account.getUserName())).thenReturn(account);
        String actualDescription = accountCommands.describeAccount();

        // Then
        Assertions.assertEquals(expectedDescription, actualDescription);
    }

    @Test
    public void describeAccountShouldReturnYouAreNotSignedInIfNobodyHasSignedIn() throws NotFoundException {

        // Given
        String expectedDescription = "You are not signed in";

        // When
        String actualDescription = accountCommands.describeAccount();

        // Then
        Assertions.assertEquals(expectedDescription, actualDescription);
    }


}
