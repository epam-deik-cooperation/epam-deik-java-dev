package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.checkout.OrderService;
import com.epam.training.webshop.core.checkout.model.OrderDto;
import com.epam.training.webshop.core.user.UserService;
import java.util.List;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class OrderCommand {

    private final UserService userService;
    private final OrderService orderService;

    public OrderCommand(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "user order list", value = "List previous orders")
    public List<OrderDto> listOrders() {
        return orderService.retrieveOrdersForUser(userService.getLoggedInUser().get());
    }

    private Availability isLoggedIn() {
        return userService.getLoggedInUser().isPresent()
            ? Availability.available()
            : Availability.unavailable("You are not logged in!");
    }
}
