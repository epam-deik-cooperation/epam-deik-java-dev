package hu.unideb.inf.ticketservice.command.account;

import hu.unideb.inf.ticketservice.command.impl.account.DescribeAccountCommand;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDescribeAccountCommand {

    private DescribeAccountCommand underTest;
    private LoggedInUserTrackService loggedInUserTrackService;
    private AdminCredentialsProvider credentialsProvider;

    @BeforeEach
    public void setup()
    {
        credentialsProvider = new AdminCredentialsProvider();
        loggedInUserTrackService = new LoggedInUserTrackImpl(new DefaultUser());
        underTest = new DescribeAccountCommand(loggedInUserTrackService);
    }

    @Test
    public void testExecuteShouldReturnCorrectResultWhenGivenPrivilegedAccount()
    {
        //Given
        String expected = "Signed in with privileged account 'admin'";
        loggedInUserTrackService.updateCurrentUser(new Administrator(credentialsProvider));

        //When
        String result = underTest.execute(null);

        //Then
        Assertions.assertEquals(expected,result);
    }
    @Test
    public void testExecuteShouldReturnCorrectStringWhenGivenDefaultAccount()
    {
        //Given
        String expected = "You are not signed in";

        //When
        String result = underTest.execute(null);

        //Then
        Assertions.assertEquals(expected,result);
    }

}
