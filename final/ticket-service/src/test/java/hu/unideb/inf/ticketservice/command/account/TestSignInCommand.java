package hu.unideb.inf.ticketservice.command.account;

import hu.unideb.inf.ticketservice.command.impl.account.SignInCommand;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.model.user.User;
import hu.unideb.inf.ticketservice.repository.UserRepository;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.impl.UserRepositoryConnection;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class TestSignInCommand {

    private SignInCommand underTest;
    private LoggedInUserTrackService loggedInUserTrackService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        loggedInUserTrackService = new LoggedInUserTrackImpl(new DefaultUser());
        underTest = new SignInCommand(new UserRepositoryConnection(userRepository), loggedInUserTrackService);
    }

    @Test
    public void testExecuteShouldChangeCurrentAccountWhenGivenRightCredentials()
    {
        //Given
        final String expected = "Successfully signed in";
        final User user = new User("Test", "Test", false);
        BDDMockito.given(userRepository.findAll()).willReturn(List.of(user));

        //When
        final String result = underTest.execute(List.of("Test","Test"));
        final User resultUser = loggedInUserTrackService.getCurrentUser();

        //Then
        Assertions.assertEquals(expected,result);
        Assertions.assertEquals(user,resultUser);
    }

    @Test
    public void testExecuteShouldNotChangeCurrentAccountWhenGivenWrongCredentials()
    {
        //Given
        final String expected = "Login failed due to incorrect credentials";
        final User user = new User("Test", "Test", false);
        BDDMockito.given(userRepository.findAll()).willReturn(List.of(user));
        final User expectedUser = new DefaultUser();

        //When
        final String result = underTest.execute(List.of("Test","TestPass"));
        final User resultUser = loggedInUserTrackService.getCurrentUser();

        //Then
        Assertions.assertEquals(expected,result);
        Assertions.assertEquals(expectedUser,resultUser);
    }

}
