package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Currency;
import java.util.List;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class ProductCommand {

    private final ProductService productService;

    public ProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @ShellMethod(key = "user product list", value = "List all available products")
    public List<Product> listAvailableProducts() {
        return productService.getProductList();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "admin create product", value = "Create new product")
    public Product createProduct(String name, Double amount, Currency currency) {
        Product product = Product.builder()
            .withName(name)
            .withNetPrice(new Money(amount, currency))
            .build();
        productService.createProduct(product);
        return product;
    }

    private Availability isAvailable() {
        return Availability.available();
    }
}
