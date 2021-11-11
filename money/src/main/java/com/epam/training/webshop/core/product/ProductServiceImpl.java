package com.epam.training.webshop.core.product;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private List<Product> productList;

    public void init() {
        productList = new LinkedList<>(List.of(
            Product.builder()
                .withName("GPU")
                .withNetPrice(new Money(600_000, Currency.getInstance("HUF")))
                .build(),
            Product.builder()
                .withName("PS5")
                .withNetPrice(new Money(500_000, Currency.getInstance("HUF")))
                .build()
        ));
    }

    @Override
    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public Optional<Product> getProductByName(String productName) {
        return productList.stream()
            .filter(name -> name.getName().equals(productName))
            .findFirst();
    }

    @Override
    public void createProduct(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");
        Objects.requireNonNull(product.getName(), "Product name cannot be null");
        Objects.requireNonNull(product.getNetPrice(), "Product net price cannot be null");
        productList.add(product);
    }
}
