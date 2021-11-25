package com.epam.training.webshop.core.checkout.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.training.webshop.core.checkout.model.OrderDto;
import com.epam.training.webshop.core.checkout.persistence.entity.Order;
import com.epam.training.webshop.core.checkout.persistence.entity.OrderItem;
import com.epam.training.webshop.core.checkout.persistence.repository.OrderRepository;
import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.ProductDto;
import com.epam.training.webshop.core.user.UserService;
import com.epam.training.webshop.core.user.model.UserDto;
import com.epam.training.webshop.core.user.persistence.entity.User;
import com.epam.training.webshop.core.user.persistence.repository.UserRepository;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    private static final String HUF_CURRENCY_CODE = "HUF";
    private static final Currency HUF_CURRENCY = Currency.getInstance(HUF_CURRENCY_CODE);
    private static final String PRODUCT_NAME = "TV";
    private static final double NET_PRICE_AMOUNT = 100;
    private static final double GROSS_PRICE_AMOUNT = 200;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserDto userDto;

    @Mock
    private OrderDto orderDto;

    @InjectMocks
    private OrderServiceImpl underTest;

    @Test
    public void testCreateOrderShouldThrowNullPointerExceptionWhenOrderDtoIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.createOrder(null, userDto));
    }

    @Test
    public void testCreateOrderShouldThrowNullPointerExceptionWhenUserDtoIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.createOrder(orderDto, null));
    }

    @Test
    public void testCreateOrderShouldThrowIllegalArgumentExceptionWhenTheUserDoesNotExist() {
        // Given
        when(userDto.getUsername()).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.createOrder(orderDto, userDto));

        // Then
        verify(userDto).getUsername();
        verify(userRepository).findByUsername("username");
    }

    @Test
    public void testCreateOrderShouldCallOrderRepositorySaveMethodWhenTheParametersAreValid() {
        // Given
        User user = mock(User.class);
        when(userDto.getUsername()).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        Order expectedOrder = createOrderEntity(user);

        // When
        underTest.createOrder(createOrderDto(), userDto);

        // Then
        verify(userDto).getUsername();
        verify(userRepository).findByUsername("username");
        verify(orderRepository).save(expectedOrder);
    }

    @Test
    public void testRetrieveOrdersForUserShouldThrowNullPointerExceptionWhenUserDtoIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.retrieveOrdersForUser(null));
    }

    @Test
    public void testRetrieveOrdersForUserShouldReturnWithAListOfOrdersWhenUserDtoIsNotNull() {
        // Given
        User user = mock(User.class);
        when(userDto.getUsername()).thenReturn("username");
        when(orderRepository.findByUserUsername("username")).thenReturn(List.of(createOrderEntity(user)));
        List<OrderDto> expected = List.of(createOrderDto());

        // When
        List<OrderDto> actual = underTest.retrieveOrdersForUser(userDto);

        // Then
        assertEquals(expected, actual);
        verify(userDto).getUsername();
        verify(orderRepository).findByUserUsername("username");
    }

    @Test
    public void testRetrieveOrdersForUserShouldReturnWithANEmptyListWhenThereIsNoOrderForTheGivenUser() {
        // Given
        when(userDto.getUsername()).thenReturn("username");
        when(orderRepository.findByUserUsername("username")).thenReturn(Collections.emptyList());
        List<OrderDto> expected = Collections.emptyList();

        // When
        List<OrderDto> actual = underTest.retrieveOrdersForUser(userDto);

        // Then
        assertEquals(expected, actual);
        verify(userDto).getUsername();
        verify(orderRepository).findByUserUsername("username");
    }

    @Test
    public void testHandleOrderShouldThrowNullPointerExceptionWhenOrderDtoIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.handleOrder(null));
    }

    @Test
    public void testHandleOrderShouldThrowIllegalArgumentExceptionWhenThereIsNoUserLoggedIn() {
        // Given
        when(userService.getLoggedInUser()).thenReturn(Optional.empty());

        // When - Then
        assertThrows(IllegalArgumentException.class, () -> underTest.handleOrder(orderDto));

        // Then
        verify(userService).getLoggedInUser();
    }

    private Order createOrderEntity(User user) {
        OrderItem orderItem = OrderItem.builder()
            .name(PRODUCT_NAME)
            .netPriceAmount(NET_PRICE_AMOUNT)
            .netPriceCurrencyCode(HUF_CURRENCY_CODE)
            .build();
        return Order.builder()
            .orderItemList(List.of(orderItem))
            .user(user)
            .netPriceAmount(NET_PRICE_AMOUNT)
            .netPriceCurrencyCode(HUF_CURRENCY_CODE)
            .grossPriceAmount(GROSS_PRICE_AMOUNT)
            .grossPriceCurrencyCode(HUF_CURRENCY_CODE)
            .build();
    }

    private OrderDto createOrderDto() {
        Money orderNetPrice = new Money(NET_PRICE_AMOUNT, HUF_CURRENCY);
        Money orderGrossPrice = new Money(GROSS_PRICE_AMOUNT, HUF_CURRENCY);
        ProductDto productDto = ProductDto.builder()
            .withName(PRODUCT_NAME)
            .withNetPrice(new Money(NET_PRICE_AMOUNT, HUF_CURRENCY))
            .build();
        return new OrderDto(List.of(productDto), orderNetPrice, orderGrossPrice);
    }
}
