package com.epam.training.webshop.core.checkout.impl;

import com.epam.training.webshop.core.checkout.CheckoutObserver;
import com.epam.training.webshop.core.checkout.OrderService;
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
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService, CheckoutObserver {

    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(UserService userService, UserRepository userRepository, OrderRepository orderRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void handleOrder(OrderDto orderDto) {
        Objects.requireNonNull(orderDto, "OrderDto cannot be null");
        UserDto userDto = userService.getLoggedInUser().orElseThrow(() -> new IllegalArgumentException("You need to first login!"));
        createOrder(orderDto, userDto);
    }

    @Override
    public void createOrder(OrderDto orderDto, UserDto userDto) {
        Objects.requireNonNull(orderDto, "OrderDto cannot be null");
        Objects.requireNonNull(userDto, "UserDto cannot be null");
        User user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid username!"));
        Order order = Order.builder()
            .orderItemList(orderDto.getProductList().stream().map(this::createOrderItemFromProduct).collect(Collectors.toList()))
            .user(user)
            .netPriceAmount(orderDto.getNetPrice().getValue())
            .grossPriceAmount(orderDto.getGrossPrice().getValue())
            .netPriceCurrencyCode(orderDto.getNetPrice().getCurrency().getCurrencyCode())
            .grossPriceCurrencyCode(orderDto.getGrossPrice().getCurrency().getCurrencyCode())
            .build();
        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> retrieveOrdersForUser(UserDto userDto) {
        Objects.requireNonNull(userDto, "UserDto cannot be null");
        List<Order> orderList = orderRepository.findByUserUsername(userDto.getUsername());
        return orderList.stream().map(this::createOrderDtoFromEntity).collect(Collectors.toList());
    }

    private OrderDto createOrderDtoFromEntity(Order order) {
        return new OrderDto(
            order.getOrderItemList().stream().map(this::createProductFromOrderItem).collect(Collectors.toList()),
            new Money(order.getNetPriceAmount(), Currency.getInstance(order.getNetPriceCurrencyCode())),
            new Money(order.getGrossPriceAmount(), Currency.getInstance(order.getGrossPriceCurrencyCode()))
        );
    }

    private ProductDto createProductFromOrderItem(OrderItem orderItem) {
        return ProductDto.builder()
            .withName(orderItem.getName())
            .withNetPrice(new Money(orderItem.getNetPriceAmount(), Currency.getInstance(orderItem.getNetPriceCurrencyCode())))
            .build();
    }

    private OrderItem createOrderItemFromProduct(ProductDto productDto) {
        return OrderItem.builder()
            .name(productDto.getName())
            .netPriceAmount(productDto.getNetPrice().getValue())
            .netPriceCurrencyCode(productDto.getNetPrice().getCurrency().getCurrencyCode())
            .build();
    }
}
