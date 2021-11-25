package com.epam.training.webshop.core.checkout;

import com.epam.training.webshop.core.checkout.model.OrderDto;
import com.epam.training.webshop.core.user.model.UserDto;
import java.util.List;

public interface OrderService {

    void createOrder(OrderDto orderDto, UserDto userDto);

    List<OrderDto> retrieveOrdersForUser(UserDto userDto);
}
