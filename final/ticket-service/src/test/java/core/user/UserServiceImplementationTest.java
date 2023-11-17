package core.user;

import com.epam.training.ticketservice.core.model.User;
import com.epam.training.ticketservice.core.repository.UserRepo;
import com.epam.training.ticketservice.core.service.implementations.UserServiceImplementation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplementationTest {
    private final UserRepo userRepo = mock(UserRepo.class);
    private final UserServiceImplementation testUserServiceImplementation = new UserServiceImplementation(userRepo);
    @Test
    void testLoginShouldSetLoggedInUserWhenUsernameAndPasswordAreCorrect(){
        //Given
        User user = new User("admin", "admin", User.Role.ADMIN);
        when(userRepo.findByNameAndPassword(user.getName(), user.getPassword())).thenReturn(Optional.of(user));

        //When
        String actual = testUserServiceImplementation.login(user.getName(), user.getPassword());

        //Then
        assertEquals("Successfully signed in", actual);
        assertEquals(user, testUserServiceImplementation.getLoggedInUser());
    }

    @Test
    void testLoginShouldReturnIncorrectCredentialsWhenUsernameAndPasswordAreNotCorrect(){
        //Given
        User user = new User("dummy", "dummy", User.Role.USER);

        //When
        String actual = testUserServiceImplementation.login(user.getName(), user.getPassword());

        //Then
        assertEquals("Login failed due to incorrect credentials", actual);
        assertNull(testUserServiceImplementation.getLoggedInUser());
    }

    @Test
    void testLoginShouldReturnAlreadySignedInWhenUserAlreadySignedIn(){
        //Given
        User user = new User("admin", "admin", User.Role.ADMIN);
        when(userRepo.findByNameAndPassword(user.getName(), user.getPassword())).thenReturn(Optional.of(user));

        //When
        testUserServiceImplementation.login(user.getName(), user.getPassword());
        String actual = testUserServiceImplementation.login(user.getName(), user.getPassword());

        //Then
        assertEquals("User already signed in", actual);
        assertEquals(user, testUserServiceImplementation.getLoggedInUser());
    }

    @Test
    void testLogoutShouldReturnNotSignedInWhenUserNotSignedIn(){
        //Given
        User user = new User("admin", "admin", User.Role.ADMIN);
        when(userRepo.findByNameAndPassword(user.getName(), user.getPassword())).thenReturn(Optional.of(user));

        //When
        String actual = testUserServiceImplementation.logout();

        //Then
        assertEquals("You are not signed in", actual);
        assertNull(testUserServiceImplementation.getLoggedInUser());
    }

    @Test
    void testLogoutShouldReturnSignedOutWhenUserSignedIn(){
        //Given
        User user = new User("admin", "admin", User.Role.ADMIN);
        when(userRepo.findByNameAndPassword(user.getName(), user.getPassword())).thenReturn(Optional.of(user));

        //When
        testUserServiceImplementation.login(user.getName(), user.getPassword());
        String actual = testUserServiceImplementation.logout();

        //Then
        assertEquals("Successfully signed out", actual);
        assertNull(testUserServiceImplementation.getLoggedInUser());
    }

    @Test
    void testDescribeAccountShouldReturnAccountInformationWhenUserSignedIn(){
        //Given
        User user = new User("admin", "admin", User.Role.ADMIN);
        when(userRepo.findByNameAndPassword(user.getName(), user.getPassword())).thenReturn(Optional.of(user));

        //When
        testUserServiceImplementation.login(user.getName(), user.getPassword());
        String actual = testUserServiceImplementation.describeAccount();

        //Then
        assertEquals("Signed in with privileged account 'admin'", actual);
        assertEquals(user, testUserServiceImplementation.getLoggedInUser());
    }

    @Test
    void testDescribeAccountShouldReturnAccountInformationWhenUserNotSignedIn(){
        //Given
        User user = new User("admin", "admin", User.Role.ADMIN);
        when(userRepo.findByNameAndPassword(user.getName(), user.getPassword())).thenReturn(Optional.of(user));

        //When
        String actual = testUserServiceImplementation.describeAccount();

        //Then
        assertEquals("You are not signed in", actual);
        assertNull(testUserServiceImplementation.getLoggedInUser());
    }
}
