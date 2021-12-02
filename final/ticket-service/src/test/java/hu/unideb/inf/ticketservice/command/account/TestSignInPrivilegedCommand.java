package hu.unideb.inf.ticketservice.command.account;

import hu.unideb.inf.ticketservice.command.impl.account.SignInPrivilegedCommand;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.model.user.UserInterface;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestSignInPrivilegedCommand {

    private SignInPrivilegedCommand underTest;
    private LoggedInUserTrackService loggedInUserTrackService;
    private AdminCredentialsProvider credentialsProvider;

    @BeforeEach
    public void setup()
    {
        credentialsProvider = new AdminCredentialsProvider();
        loggedInUserTrackService = new LoggedInUserTrackImpl(new DefaultUser());
        underTest = new SignInPrivilegedCommand(loggedInUserTrackService,new AdminCredentialsProvider());
    }

    @Test
    public void testExecuteShouldChangeCurrentAccountWhenGivenRightCredentials()
    {
        //Given
        final String expected = "Signed in";
        final UserInterface expectedUser = new Administrator(credentialsProvider);

        //When
        final String result = underTest.execute(List.of("admin","admin"));
        final UserInterface resultUser = loggedInUserTrackService.getCurrentUser();

        //Then
        Assertions.assertEquals(expected,result);
        Assertions.assertEquals(expectedUser,resultUser);
    }

    @Test
    public void testExecuteShouldNotChangeCurrentAccountWhenGivenWrongCredentials()
    {
        //Given
        final String expected = "Login failed due to incorrect credentials";
        final UserInterface expectedUser = loggedInUserTrackService.getCurrentUser();

        //When
        final String result = underTest.execute(List.of("admin","adminWrong"));
        final UserInterface resultUser = loggedInUserTrackService.getCurrentUser();

        //Then
        Assertions.assertEquals(expected,result);
        Assertions.assertEquals(expectedUser,resultUser);
    }

}
