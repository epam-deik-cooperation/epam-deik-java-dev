package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.ProductDto;
import com.epam.training.webshop.core.user.UserService;
import com.epam.training.webshop.core.user.model.UserDto;
import com.epam.training.webshop.core.user.persistence.entity.User;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class ProductCommand {

    private final ProductService productService;
    private final UserService userService;

    public ProductCommand(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @ShellMethod(key = "user product list", value = "List all available products")
    public List<ProductDto> listAvailableProducts() {
        return productService.getProductList();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "admin create product", value = "Create new product")
    public ProductDto createProduct(String name, Double amount, Currency currency) {
        ProductDto product = ProductDto.builder()
            .withName(name)
            .withNetPrice(new Money(amount, currency))
            .build();
        productService.createProduct(product);
        return product;
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.getLoggedInUser();
        if (user.isPresent() && user.get().getRole() == User.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You are not an admin!");
    }
}
