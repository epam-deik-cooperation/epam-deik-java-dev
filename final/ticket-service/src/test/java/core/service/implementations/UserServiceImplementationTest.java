package core.service.implementations;

import com.epam.training.ticketservice.core.dto.UserDto;
import com.epam.training.ticketservice.core.model.User;
import com.epam.training.ticketservice.core.repository.UserRepository;
import com.epam.training.ticketservice.core.service.implementations.UserServiceImplementation;
import com.epam.training.ticketservice.core.service.interfaces.UserServiceInterface;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class UserServiceImplementationTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserServiceInterface underTest = new UserServiceImplementation(userRepository);
    User admin = new User("admin", "admin", User.Role.ADMIN);
    User dummy = new User("dummy", "dummy", User.Role.USER);
    @Test
    void testLoginShouldSetLoggedInUserWhenUsernameAndPasswordAreCorrect(){
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
    void testLoginShouldReturnOptionalEmptyWhenUsernameAndPasswordAreNotCorrect(){
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
    void testLoginShouldReturnOptionalUserDtoEmptyWhenRoleIsNotAdmin() {
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
        boolean isValid = underTest.isValidCredentials(dummy, dummy.getPassword());

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
}
