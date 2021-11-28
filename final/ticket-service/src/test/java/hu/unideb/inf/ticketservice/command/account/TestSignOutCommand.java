package hu.unideb.inf.ticketservice.command.account;

import hu.unideb.inf.ticketservice.command.impl.account.SignOutCommand;
import hu.unideb.inf.ticketservice.model.user.AbstractUser;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.service.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSignOutCommand {

    private SignOutCommand underTest;
    private LoggedInUserTrackService loggedInUserTrackService;
    private AdminCredentialsProvider credentialsProvider;

    @BeforeEach
    public void setup()
    {
        credentialsProvider = new AdminCredentialsProvider();
        loggedInUserTrackService = new LoggedInUserTrackImpl(new DefaultUser());
        underTest = new SignOutCommand(loggedInUserTrackService);
    }

    @Test
    public void testExecuteShouldChangeCurrentAccountToDefaultWhenSignedIn()
    {
        //Given
        final String expected = "Signed out";
        final AbstractUser expectedUser = loggedInUserTrackService.getCurrentUser();
        loggedInUserTrackService.updateCurrentUser(new Administrator(credentialsProvider));

        //When
        final String result = underTest.execute(null);
        final AbstractUser resultUser = loggedInUserTrackService.getCurrentUser();

        //Then
        Assertions.assertEquals(expected,result);
        Assertions.assertEquals(expectedUser,resultUser);
    }

    @Test
    public void testExecuteShouldNotChangeCurrentAccountWhenNotSignedIn()
    {
        //Given
        final String expected = "You are not signed in";
        final AbstractUser expectedUser = loggedInUserTrackService.getCurrentUser();

        //When
        final String result = underTest.execute(null);
        final AbstractUser resultUser = loggedInUserTrackService.getCurrentUser();

        //Then
        Assertions.assertEquals(expected,result);
        Assertions.assertEquals(expectedUser,resultUser);
    }

}
