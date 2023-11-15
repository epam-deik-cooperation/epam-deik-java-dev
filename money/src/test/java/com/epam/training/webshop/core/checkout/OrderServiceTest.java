package com.epam.training.webshop.core.checkout;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.epam.training.webshop.core.checkout.model.OrderDto;
import com.epam.training.webshop.core.checkout.persistence.Order;
import com.epam.training.webshop.core.checkout.persistence.OrderRepository;
import com.epam.training.webshop.core.user.UserService;
import com.epam.training.webshop.core.user.model.UserDto;
import com.epam.training.webshop.core.user.persistence.User;
import com.epam.training.webshop.core.user.persistence.UserRepository;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private static final User USER = new User("user", "pw", User.Role.USER);
    private static final UserDto USER_DTO = new UserDto("user", User.Role.USER);
    private static final OrderDto ORDER_DTO = new OrderDto(Map.of(), null, null);
    private static final Order ORDER = new Order();

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService underTest;

    @Test
    void testHandleOrderShouldThrowExceptionWhenUserIsNotLoggedIn() {
        // Given
        when(userService.describe()).thenReturn(Optional.empty());

        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.handleOrder(ORDER_DTO));

        // Then
        verify(userService).describe();
        verifyNoMoreInteractions(userRepository, userService, orderRepository);
    }

    @Test
    void testHandleOrderShouldThrowExceptionWhenUserDoesNotExist() {
        // Given
        when(userService.describe()).thenReturn(Optional.of(USER_DTO));
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.handleOrder(ORDER_DTO));

        // Then
        verify(userService).describe();
        verify(userRepository).findByUsername("user");
        verifyNoMoreInteractions(userRepository, userService, orderRepository);
    }

    @Test
    void testHandleOrderShouldSaveTheOrder() {
        // Given
        when(userService.describe()).thenReturn(Optional.of(USER_DTO));
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(USER));
        when(orderRepository.save(any())).thenReturn(ORDER);

        // When
        underTest.handleOrder(ORDER_DTO);

        // Then
        verify(userService).describe();
        verify(userRepository).findByUsername("user");
        verify(orderRepository).save(any());
        verifyNoMoreInteractions(userRepository, userService, orderRepository);
    }
}
