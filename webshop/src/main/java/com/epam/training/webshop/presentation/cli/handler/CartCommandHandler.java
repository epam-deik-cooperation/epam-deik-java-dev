package com.epam.training.webshop.presentation.cli.handler;

import com.epam.training.webshop.model.Order;
import com.epam.training.webshop.service.ShoppingCartService;
import com.epam.training.webshop.service.exception.NoSuchProductException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CartCommandHandler {

    private final ShoppingCartService shoppingCartService;

    public CartCommandHandler(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @ShellMethod(value = "Order the cart", key = "order cart")
    public String orderCart() {
        final Order order = shoppingCartService.order();
        return String.format("Ordered a cart of products, net price is %s HUF, gross price is %s HUF.", order.getFullNetPrice(), order.getFullGrossPrice());
    }

    @ShellMethod(value = "Adds a product to the cart", key = "add product")
    public String addProduct(final String productName) {
        try {
            shoppingCartService.addProduct(productName);
            return "Alright.";
        } catch (NoSuchProductException e) {
            return "No such product";
        }
    }
}
