package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.ProductDto;
import com.epam.training.webshop.core.user.UserService;
import com.epam.training.webshop.core.user.model.UserDto;
import com.epam.training.webshop.core.user.persistence.User;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class ProductCommand {

    private final ProductService productService;
    private final UserService userService;

    @ShellMethod(key = "user product list", value = "List the available products.")
    public List<ProductDto> listProduct() {
        return productService.getProductList();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "admin product create", value = "Create a new product.")
    public ProductDto createProduct(String name, Double amount) {
        ProductDto productDto = ProductDto.builder()
            .withName(name)
            .withNetPrice(new Money(amount, Currency.getInstance("HUF")))
            .build();
        productService.createProduct(productDto);
        return productDto;
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        if (user.isEmpty()) {
            return Availability.unavailable("You are not logged in!");
        } else if (user.get().role() != User.Role.ADMIN) {
            return Availability.unavailable("You are not an admin!");
        }
        return Availability.available();
    }
}
