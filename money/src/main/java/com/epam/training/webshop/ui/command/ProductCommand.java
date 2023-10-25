package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Currency;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class ProductCommand {

    private final ProductService productService;

    @ShellMethod(key = "user product list", value = "List the available products.")
    public List<Product> listProduct() {
        return productService.getProductList();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "admin product create", value = "Create a new product.")
    public Product createProduct(String name, Double amount) {
        Product product = Product.builder()
            .withName(name)
            .withNetPrice(new Money(amount, Currency.getInstance("HUF")))
            .build();
        productService.createProduct(product);
        return product;
    }

    private Availability isAvailable() {
        return Availability.unavailable("You are not an admin!");
    }
}
