package com.epam.training.money.product;

import com.epam.training.money.Money;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");

    private final List<Product> productList = List.of(
            new Product("Pentium3", new Money(500, HUF_CURRENCY)),
            new Product("Pentium4", new Money(5000, HUF_CURRENCY)),
            new Product("Pentium5", new Money(50000, HUF_CURRENCY))
    );

    @Override
    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public Optional<Product> getProductByName(String productName) {
        return productList.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst();
    }
}
