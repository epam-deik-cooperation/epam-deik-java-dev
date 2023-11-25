package core.service.implementations;

import com.epam.training.ticketservice.core.dto.UserDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
import com.epam.training.ticketservice.core.model.User;
import com.epam.training.ticketservice.core.repository.UserRepository;
import com.epam.training.ticketservice.core.service.implementations.UserServiceImplementation;
import com.epam.training.ticketservice.core.service.interfaces.UserServiceInterface;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplementationTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserServiceInterface underTest = new UserServiceImplementation(userRepository);
    User admin = new User("admin", "admin", User.Role.ADMIN);
    User user = new User("user", "user", User.Role.USER);
    User dummy = new User("dummy", "dummy", User.Role.USER);
    @Test
    void testSignInPrivilegedShouldSetLoggedInUserWhenUsernameAndPasswordAreCorrect(){
        // Given
        when(userRepository.findByUserName(admin.getUserName())).thenReturn(Optional.of(admin));

        // When
        Optional<UserDto> actual = underTest.signInPrivileged(admin.getUserName(), admin.getPassword());

        // Then
        assertTrue(actual.isPresent());
        assertEquals(admin.getUserName(), actual.get().userName());
        assertEquals(User.Role.ADMIN, actual.get().role());
        verify(userRepository).findByUserName(admin.getUserName());
    }

    @Test
    void testSignInPrivilegedShouldReturnOptionalEmptyWhenUsernameAndPasswordAreNotCorrect(){
        //Given
        Optional<UserDto> excepted = Optional.empty();
        when(userRepository.findByUserName("dummy")).thenReturn(Optional.empty());

        //When
        Optional<UserDto> actual = underTest.signInPrivileged(dummy.getUserName(), dummy.getPassword());

        //Then
        assertEquals(excepted, actual);
        verify(userRepository).findByUserName("dummy");
    }

    @Test
    void testSignInPrivilegedShouldReturnOptionalUserDtoEmptyWhenRoleIsNotAdmin() {
        // Given
        when(userRepository.findByUserName("dummy")).thenReturn(Optional.of(dummy));

        // When
        Optional<UserDto> actual = underTest.signInPrivileged(dummy.getUserName(), "wrong-password");

        // Then
        assertTrue(actual.isEmpty());
        verify(userRepository).findByUserName("dummy");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testIsValidCredentialsShouldReturnFalseForInvalidCredentials() {
        // Given

        // When
        boolean isValid = underTest.isValidCredentialsPrivileged(dummy, dummy.getPassword());

        // Then
        assertFalse(isValid);
    }

    @Test
    void testLogoutShouldReturnOptionalEmptyWhenUserNotSignedIn(){
        //Given
        Optional<UserDto> excepted = Optional.empty();

        //When
        Optional<UserDto> actual = underTest.logout();

        //Then
        assertEquals(excepted, actual);
    }

    @Test
    void testSignUpShouldSaveUserInDatabaseWhenTheUserNameIsUnique() throws AlreadyExists {
        // Given
        when(userRepository.findByUserName("user")).thenReturn(Optional.empty());

        // When
        underTest.signUp(user.getUserName(), user.getPassword());

        // Then
        verify(userRepository).findByUserName(user.getUserName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testSignUpShouldNotSaveUserInDatabaseAndReturnAlreadyExistsWhenTheUserNameIsNotUnique(){
        // Given
        when(userRepository.findByUserName("user")).thenReturn(Optional.of(user));

        // When
        assertThrows(AlreadyExists.class, () -> underTest.signUp(user.getUserName(), user.getPassword()));

        // Then
        verify(userRepository).findByUserName(user.getUserName());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testSignInShouldSetLoggedInUserWhenUsernameAndPasswordAreCorrect() {
        // Given
        when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.of(user));

        // When
        Optional<UserDto> actual = underTest.signIn(user.getUserName(), user.getPassword());

        // Then
        assertTrue(actual.isPresent());
        verify(userRepository).findByUserName(user.getUserName());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testSignInShouldReturnOptionalEmptyWhenRoleIsNotUser() {
        // Given
        when(userRepository.findByUserName(admin.getUserName())).thenReturn(Optional.empty());

        // When
        Optional<UserDto> actual = underTest.signIn(admin.getUserName(), admin.getPassword());

        // Then
        assertTrue(actual.isEmpty());
        verify(userRepository).findByUserName(admin.getUserName());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testSignInShouldReturnOptionalEmptyWhenRoleIsNotUserAndUserIsNotPresent() {
        // Given
        when(userRepository.findByUserName(admin.getUserName())).thenReturn(Optional.of(admin));

        // When
        Optional<UserDto> actual = underTest.signIn(admin.getUserName(), "adminPassword");

        // Then
        assertTrue(actual.isEmpty());
        verify(userRepository).findByUserName(admin.getUserName());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testIsValidCredentialsShouldReturnFalseForInvalidUserRole() {
        // Given
        User invalidUserRoleUser = new User("admin", "admin", User.Role.ADMIN);

        // When
        boolean result = underTest.isValidCredentials(invalidUserRoleUser, "admin");

        // Then
        assertFalse(result);
    }
}
