package hu.unideb.inf.ticketservice.command.account;

import hu.unideb.inf.ticketservice.command.impl.account.SignUpCommand;
import hu.unideb.inf.ticketservice.model.user.User;
import hu.unideb.inf.ticketservice.repository.UserRepository;
import hu.unideb.inf.ticketservice.service.connection.impl.UserRepositoryConnection;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class TestSignUpCommand {

    private SignUpCommand underTest;
    private AdminCredentialsProvider adminCredentialsProvider;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        adminCredentialsProvider = new AdminCredentialsProvider();
        underTest = new SignUpCommand(new UserRepositoryConnection(userRepository), adminCredentialsProvider);
    }

    @Test
    public void testExecuteShouldSignUpAccountWhenGivenNonAdminNonDefaultUsername()
    {
        //Given
        final User user = new User("Test", "Test", false);
        final String expected = "Successfully signed up";

        //When
        final String result = underTest.execute(List.of("Test", "Test"));

        //Then
        Assertions.assertEquals(expected,result);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void testExecuteShouldNotSignUpAccountWhenGivenAdminUsername()
    {
        //Given
        final String expected = "The username cannot be admin";

        //When
        final String result = underTest.execute(List.of("admin", "Test"));

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldNotSignUpAccountWhenGivenDefaultUsername()
    {
        //Given
        final String expected = "The username cannot be default";

        //When
        final String result = underTest.execute(List.of("default", "Test"));

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldNotSignUpAccountWhenGivenAlreadyExistingAccountCredentials()
    {
        //Given
        final List<User> userList = List.of(new User("Test", "Test",false));
        BDDMockito.given(userRepository.findAll()).willReturn(userList);
        final String expected = "There is already a username with Test";

        //When
        final String result = underTest.execute(List.of("Test", "Test"));

        //Then
        Assertions.assertEquals(expected,result);
    }

}
